package bbarena.model

interface ChoiceFilter {

    fun filter(choices: Set<Choice>): Set<Choice>

}
