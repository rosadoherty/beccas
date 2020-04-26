import java.util.*;

public class SubsequenceLogic {
    public static List<Integer> longestConsecutiveIntegerList(List<Integer> integerList) {
        List<Integer> list = new ArrayList<>();

        for (int num : integerList) {
            if (list.size() == 0 || num > list.get(list.size() - 1)) {
                list.add(num);
            } else {
                int i = 0;
                int j = list.size() - 1;

                while (i < j) {
                    int mid = (i + j) / 2;
                    if (list.get(mid) < num) {
                        i = mid + 1;
                    } else {
                        j = mid;
                    }
                }
                list.set(j, num);
            }
        }
        return list;
    }
}