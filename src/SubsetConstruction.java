import java.util.*;
public class SubsetConstruction {
    static int dfaStateCount = 0;

    static Set<State> epsilonClosure(Set<State> states) {
        Stack<State> stack = new Stack<>();
        Set<State> closure = new HashSet<>(states);

        for (State s : states) stack.push(s);

        while (!stack.isEmpty()) {
            State state = stack.pop();
            if (state.transitions.containsKey('ε')) {
                for (State next : state.transitions.get('ε')) {
                    if (!closure.contains(next)) {
                        closure.add(next);
                        stack.push(next);
                    }
                }
            }
        }

        return closure;
    }

    static Set<State> move(Set<State> states, char symbol) {
        Set<State> result = new HashSet<>();
        for (State state : states) {
            if (state.transitions.containsKey(symbol)) {
                result.addAll(state.transitions.get(symbol));
            }
        }
        return result;
    }

    public static DFA convertNFAtoDFA(NFA nfa) {
        Map<Set<State>, DFAState> dfaStateMap = new HashMap<>();
        Queue<DFAState> queue = new LinkedList<>();

        Set<State> startClosure = epsilonClosure(Set.of(nfa.start));
        DFAState startDFAState = new DFAState(startClosure, dfaStateCount++);
        dfaStateMap.put(startClosure, startDFAState);
        queue.add(startDFAState);

        DFA dfa = new DFA(startDFAState);

        while (!queue.isEmpty()) {
            DFAState current = queue.poll();

            // Xác định tất cả ký tự có thể dịch chuyển
            Set<Character> symbols = new HashSet<>();
            for (State state : current.nfaStates) {
                symbols.addAll(state.transitions.keySet());
            }
            symbols.remove('ε'); // Loại bỏ epsilon-transitions

            for (char symbol : symbols) {
                Set<State> moveSet = move(current.nfaStates, symbol);
                Set<State> closureSet = epsilonClosure(moveSet);

                if (!closureSet.isEmpty()) {
                    DFAState nextState = dfaStateMap.get(closureSet);
                    if (nextState == null) {
                        nextState = new DFAState(closureSet, dfaStateCount++);
                        dfaStateMap.put(closureSet, nextState);
                        queue.add(nextState);
                        dfa.states.add(nextState);
                    }
                    current.transitions.put(symbol, nextState);
                }
            }
        }

        // Đánh dấu trạng thái kết thúc của DFA
        for (DFAState state : dfa.states) {
            for (State nfaState : state.nfaStates) {
                if (nfaState.isFinal) {
                    state.isFinal = true;
                    break;
                }
            }
        }

        return dfa;
    }
}

