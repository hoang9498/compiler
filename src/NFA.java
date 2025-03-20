import java.util.*;

class State {
    int id;
    Map<Character, List<State>> transitions = new HashMap<>();
    boolean isFinal = false;

    public State(int id) {
        this.id = id;
    }

    void addTransition(char symbol, State state) {
        transitions.computeIfAbsent(symbol, k -> new ArrayList<>()).add(state);
    }
}

class NFA {
    State start;
    State end;

    public NFA(State start, State end) {
        this.start = start;
        this.end = end;
    }

    static int stateCount = 0;

    static NFA createBasicNFA(char symbol) {
        State start = new State(stateCount++);
        State end = new State(stateCount++);
        start.addTransition(symbol, end);
        end.isFinal = true;
        return new NFA(start, end);
    }

    static NFA concatenate(NFA nfa1, NFA nfa2) {
        nfa1.end.isFinal = false;
        nfa1.end.addTransition('ε', nfa2.start);
        return new NFA(nfa1.start, nfa2.end);
    }

    static NFA union(NFA nfa1, NFA nfa2) {
        State start = new State(stateCount++);
        State end = new State(stateCount++);
        start.addTransition('ε', nfa1.start);
        start.addTransition('ε', nfa2.start);
        nfa1.end.addTransition('ε', end);
        nfa2.end.addTransition('ε', end);
        end.isFinal = true;
        return new NFA(start, end);
    }

    static NFA kleeneStar(NFA nfa) {
        State start = new State(stateCount++);
        State end = new State(stateCount++);
        start.addTransition('ε', nfa.start);
        start.addTransition('ε', end);
        nfa.end.addTransition('ε', nfa.start);
        nfa.end.addTransition('ε', end);
        end.isFinal = true;
        return new NFA(start, end);
    }

    void printNFA() {
        Set<State> visited = new HashSet<>();
        Queue<State> queue = new LinkedList<>();
        queue.add(start);

        while (!queue.isEmpty()) {
            State state = queue.poll();
            if (visited.contains(state)) continue;
            visited.add(state);

            for (Map.Entry<Character, List<State>> entry : state.transitions.entrySet()) {
                for (State nextState : entry.getValue()) {
                    System.out.println("State " + state.id + " --" + entry.getKey() + "--> State " + nextState.id);
                    queue.add(nextState);
                }
            }
        }
    }
}

