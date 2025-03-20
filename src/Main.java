//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        // Tạo NFA cho regex (a|b)*c
        NFA a = NFA.createBasicNFA('a');
        NFA b = NFA.createBasicNFA('b');
        NFA ab = NFA.union(a, b);
        NFA abStar = NFA.kleeneStar(ab);
        NFA c = NFA.createBasicNFA('c');
        NFA regex = NFA.concatenate(abStar, c);

        // In trạng thái NFA
        regex.printNFA();
        }
    }