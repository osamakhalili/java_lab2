import java.util.ArrayList;

public class calculater {


    int add(String numbers) {
        int sum = 0;
// trim string,
        numbers.trim();
        if (numbers.length() == 0) {
            return 0;
        }
        String[] numbersStrings = numbers.split("[,\n // ;  *]");
        ArrayList <Integer> negativeNumbers = new  ArrayList<Integer>();
        for (String num : numbersStrings) {
            try {
                int num1 = Integer.parseInt(num.trim());
                if(num1<0){
                    negativeNumbers.add(num1);
                }else if (num1<=1000){
                    sum += num1;
                }
            } catch (Exception ex) {

            }
        }
            if (negativeNumbers.size() > 0) {
                throw new RuntimeException("Negatives not allowed: " + negativeNumbers);
            }

        return sum;
    }
}




