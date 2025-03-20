import java.util.*;

class DFAState {
    Set<State> nfaStates;
    int id;
    Map<Character, DFAState> transitions = new HashMap<>();
    boolean isFinal = false;

    public DFAState(Set<State> nfaStates, int id) {
        this.nfaStates = nfaStates;
        this.id = id;
    }
}

class DFA {
    DFAState startState;
    List<DFAState> states = new ArrayList<>();

    public DFA(DFAState startState) {
        this.startState = startState;
        this.states.add(startState);
    }

    void printDFA() {
        for (DFAState state : states) {
            for (Map.Entry<Character, DFAState> entry : state.transitions.entrySet()) {
                System.out.println("DFA State " + state.id + " --" + entry.getKey() + "--> DFA State " + entry.getValue().id);
            }
        }
    }
}

