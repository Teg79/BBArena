package bbarena.server;

import net.sf.bbarena.model.Choice;
import net.sf.bbarena.model.Coach;
import net.sf.bbarena.model.team.Team;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.Session;
import java.io.IOException;
import java.util.*;

public class RemoteCoach extends Coach {

    private static final Logger _log = LoggerFactory.getLogger(RemoteCoach.class);

    private final Random _random;
    private final String _name;
    private int _answer = 0;
    private List<Choice> _answers;
    private Session _session;

    public RemoteCoach(Session session, String name, Team team, Choice... programmedChoices) {
        super(name, team);
        _random = new Random();
        _name = name;
        _answers = new ArrayList<>(Arrays.asList(programmedChoices));
        _session = session;
    }

    public void addAnswer(Choice choice) {
        _answers.add(choice);
    }

    @Override
    protected Choice ask(String question, Set<Choice> choices) {
        _log.info(_name + ": " + question + " " + choices.toString());
        Choice answer = null;
        try {
            _session.getBasicRemote().sendText(question + " " + choices.toString());
        } catch (IOException e) {
            _log.warn("Error sending message to " + _session.getQueryString());
        }
        answer = _answer >= _answers.size() ? null : _answers.get(_answer++);
        if (answer == null) {
            answer = randomAnswer(choices);
        }
        _log.info("Answer: " + answer);
        try {
            _session.getBasicRemote().sendText(answer.toString());
        } catch (IOException e) {
            _log.warn("Error sending message to " + _session.getQueryString());
        }

        return answer;
    }

    private Choice randomAnswer(Set<Choice> choices) {
        Choice[] array = choices.toArray(new Choice[choices.size()]);
        int pos = _random.nextInt(array.length);
        return array[pos];
    }

    @Override
    protected void say(String message, Set<Choice> choices) {
        _log.info(_name + ": " + message + " " + choices.toString());
        try {
            _session.getBasicRemote().sendText(message + " " + choices.toString());
        } catch (IOException e) {
            _log.warn("Error sending message to " + _session.getQueryString());
        }
    }

}
