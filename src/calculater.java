public class calculater {


        int add(String numbers){
            int sum = 0;
// trim string,
            numbers.trim();
            if (numbers.length()== 0){
                return 0;
            }
            String[] numbersStrings = numbers.split(",");

            try{
                int num1 = Integer.parseInt(numbersStrings[0].trim());
                sum+= num1;
            }catch(Exception ex){

            }
            try{
                int num2 = Integer.parseInt(numbersStrings[1].trim());
                sum+=num2;
            }catch(Exception ex){

            }
            return sum;
        }

    }


