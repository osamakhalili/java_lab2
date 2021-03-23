public class calculater {


    int add(String numbers) {
        int sum = 0;
// trim string,
        numbers.trim();
        if (numbers.length() == 0) {
            return 0;
        }
        String[] numbersStrings = numbers.split("[,\n // ; ]");
        for (String num : numbersStrings) {
            try {
                int num1 = Integer.parseInt(num.trim());
                sum += num1;
            } catch (Exception ex) {
            }
        }
        return sum;
    }
}




