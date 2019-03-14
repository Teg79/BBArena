package bbarena.model

import bbarena.model.team.Team
import bbarena.model.util.Concat
import mu.KotlinLogging
import java.util.*

private val logger = KotlinLogging.logger {}

abstract class Coach(val name: String, val team: Team) {
    private var filter: ChoiceFilter = object : ChoiceFilter {
        override fun filter(choices: Set<Choice>): Set<Choice> = choices
    }

    fun choice(question: String, choicesArray: Array<Choice>, vararg additionalChoices: Choice): Choice {
        val choices = LinkedHashSet(Arrays.asList(*choicesArray))
        for (choice in additionalChoices) {
            choices.add(choice)
        }
        return choice(question, choices)
    }

    fun choice(question: String, vararg choiceGroups: Array<Choice>): Choice {
        val choices = LinkedHashSet<Choice>()
        for (choicesArray in choiceGroups) {
            choices.addAll(Arrays.asList(*choicesArray))
        }
        return choice(question, choices)
    }

    fun choice(question: String, vararg choices: Choice): Choice {
        return choice(question, LinkedHashSet(Arrays.asList(*choices)))
    }

    fun <C : Choice> pick(question: String, choices: Set<C>): C {
        return choice(question, LinkedHashSet<Choice>(choices)) as C
    }

    fun choice(question: String, choices: Set<Choice>): Choice {

        val filteredChoices = filter.filter(choices)

        var res: Choice
        do {
            res = ask(question, filteredChoices)

            if (!filteredChoices.contains(res)) {
                logger.warn("Choice $res not valid, valid values are: $filteredChoices")
            }
        } while (!filteredChoices.contains(res))

        logger.info(Concat.buildLog(javaClass,
                Pair("coach", name),
                Pair("question", question),
                Pair("answer", res)))
        return res
    }

    fun notify(message: String, choices: Set<Choice>) {
        say(message, choices)
        val msg = StringBuilder()
        msg.append("notify: ")
                .append(message)
        logger.info(msg.toString())
    }

    protected abstract fun ask(question: String, choices: Set<Choice>): Choice

    protected abstract fun say(message: String, choices: Set<Choice>)

    fun setChoiceFilter(choiceFilter: ChoiceFilter) {
        filter = choiceFilter
    }

}
