package net.sf.bbarena.view;


import net.sf.bbarena.model.Arena;
import net.sf.bbarena.model.Coach;
import net.sf.bbarena.model.Match;
import net.sf.bbarena.model.team.Player;
import net.sf.bbarena.model.team.Team;
import net.sf.bbarena.view.bloodbowl.play.PitchView;
import net.sf.bbarena.view.util.PlayerSetupFile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;

public final class BBArenaGUIProto1 {

    private static final String orcNames[] = {"zug", "og", "rog", "mog", "za", "bu", "gol", "ugh", "fug", "gad", "rag", "go", "rg", "dab", "u", "i", "a", "z"};
    private static final String chaosNames[] = {"yx", "xa", "zy", "za", "y", "i", "zo", "rex", "ux", "xx", "zz"};

    private Arena arena;
    private Team homeTeam;
    private Team awayTeam;
    private long ID;

    class F extends JFrame {

        private static final long serialVersionUID = 6940597800662707096L;

        PitchView pitchView;
        ButtonGroup layoutMenu;
        ButtonGroup helpersMenu;

        F() {
            super("BBArena GUI Proto 1");
            setResizable(false);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            createComponents();
            createMenu();
            pack();
            centerToScreen();
        }

        void createMenu() {
            JMenuBar menuBar = new JMenuBar();
            // File menu
            JMenu file = new JMenu("File");
            menuBar.add(file);
            JMenu load = new JMenu("Load");
            file.add(load);
            JMenuItem item = new JMenuItem("Home setup..");
            item.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String f = getOpenFileName("Select home team setup file");
                    if (f != null) {
                        try {
                            PlayerSetupFile.setupPlayers(arena.getPitch(), homeTeam, new FileInputStream(f));
                            pitchView.updatePitchPlayers(arena.getPitch());
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            System.exit(1);
                        }
                    }
                }
            });
            load.add(item);
            item = new JMenuItem("Away setup..");
            item.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String f = getOpenFileName("Select away team setup file");
                    if (f != null) {
                        try {
                            PlayerSetupFile.setupPlayers(arena.getPitch(), awayTeam, new FileInputStream(f));
                            pitchView.updatePitchPlayers(arena.getPitch());
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            System.exit(1);
                        }
                    }
                }
            });
            load.add(item);
            JMenu save = new JMenu("Save");
            file.add(save);
            item = new JMenuItem("Home setup..");
            item.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String f = getSaveFileName("Select output file name");
                    if (f != null) {
                        PlayerSetupFile.savePlayerSetup(homeTeam, f);
                    }
                }
            });
            save.add(item);
            item = new JMenuItem("Away setup..");
            item.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String f = getSaveFileName("Select output file name");
                    if (f != null) {
                        PlayerSetupFile.savePlayerSetup(awayTeam, f);
                    }
                }
            });
            save.add(item);
            item = new JMenuItem("Exit");
            item.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
            file.add(item);
            layoutMenu = new ButtonGroup();
            // Options menu
            JMenu options = new JMenu("Options");
            JMenu layout = new JMenu("Layout");
            options.add(layout);
            item = new JRadioButtonMenuItem("Landscape");
            layoutMenu.add(item);
            item.setSelected(pitchView.getPitchLayout() == PitchView.Layout.Landscape);
            item.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (pitchView.getPitchLayout() != PitchView.Layout.Landscape) {
                        pitchView.setPitchLayout(PitchView.Layout.Landscape);
                        pack();
                        centerToScreen();
                    }
                }
            });
            layout.add(item);
            item = new JRadioButtonMenuItem("Portrait");
            layoutMenu.add(item);
            item.setSelected(pitchView.getPitchLayout() == PitchView.Layout.Portrait);
            item.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (pitchView.getPitchLayout() != PitchView.Layout.Portrait) {
                        pitchView.setPitchLayout(PitchView.Layout.Portrait);
                        pack();
                        centerToScreen();
                    }
                }
            });
            layout.add(item);
            JMenu helpers = new JMenu("Helpers");
            helpersMenu = new ButtonGroup();
            item = new JRadioButtonMenuItem("Enabled");
            item.setSelected(true);
            pitchView.setUseHelpers(true);
            item.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    pitchView.setUseHelpers(true);
                }
            });
            helpers.add(item);
            helpersMenu.add(item);
            item = new JRadioButtonMenuItem("Disabled");
            item.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    pitchView.setUseHelpers(false);
                }
            });
            helpers.add(item);
            helpersMenu.add(item);
            options.add(helpers);
            menuBar.add(options);
            // Turn menu
            JMenu turn = new JMenu("Turn");
            item = new JMenuItem("End setup");
            turn.add(item);
            item.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    pitchView.endSetup();
                    ((JMenuItem) e.getSource()).setEnabled(false);
                }
            });
            menuBar.add(turn);
            setJMenuBar(menuBar);
        }

        void createComponents() {
            createArena();
            getContentPane().setLayout(new BorderLayout());
            pitchView = new PitchView(arena);
            getContentPane().add(pitchView, BorderLayout.CENTER);
        }

        void centerToScreen() {
            Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension s = getSize();
            //setLocation() bugs in Ubuntu linux (reason? unknown)
            //setLocation((d.width - getSize().width) / 2, (d.height - getSize().height) / 2);
            setBounds((d.width - s.width) / 2, (d.height - s.height) / 2, s.width, s.height);
        }

        String getOpenFileName(String title) {
            JFileChooser fc = new JFileChooser(title);
            int result = fc.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                return fc.getSelectedFile().getAbsolutePath();
            }
            return null;
        }

        String getSaveFileName(String title) {
            JFileChooser fc = new JFileChooser(title);
            int result = fc.showSaveDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                return fc.getSelectedFile().getAbsolutePath();
            }
            return null;
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        new BBArenaGUIProto1().run();
    }

    void run() {
        new F().setVisible(true);
    }

    void createArena() {
        homeTeam = new Team(++ID, null);
        homeTeam.setName("The Chaos Team");
        createTeamPlayers(homeTeam, 1, 16, chaosNames);
        awayTeam = new Team(++ID, null);
        awayTeam.setName("The Orc Team");
        createTeamPlayers(awayTeam, 100, 16, orcNames);

        Coach homeCoach = new CoachUi(homeTeam);
        Coach awayCoach = new CoachUi(awayTeam);

        Match match = new Match(homeCoach, awayCoach);
        arena = match.getArena();
        PlayerSetupFile.setupPlayers(arena.getPitch(), homeTeam, net.sf.bbarena.view.util.FileUtils.getAsStream("data/homebasesetup.txt"));
        PlayerSetupFile.setupPlayers(arena.getPitch(), awayTeam, net.sf.bbarena.view.util.FileUtils.getAsStream("data/awaybasesetup.txt"));
    }

    void createTeamPlayers(Team t, int firstIndex, int number, String[] names) {
        while (number-- > 0) {
            t.addPlayer(new Player(++ID, firstIndex++, generateRandomName(names)));
        }
    }

    String generateRandomName(String[] a) {
        StringBuffer sb = new StringBuffer();
        Random r = new Random();
        sb.append(a[r.nextInt(a.length)]);
        int more = r.nextInt(2) + 1;
        while (more-- > 0) {
            sb.append(a[r.nextInt(a.length)]);
        }
        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        return sb.toString();
    }


}
