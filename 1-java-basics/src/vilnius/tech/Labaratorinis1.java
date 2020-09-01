package vilnius.tech;

import java.util.Random;

/**
 * @author Aurimas Šakalys PRIf-18/2 20185388
 * Klasė skirta 1 labaratorinio darbo atlikimui.
 */
public class Labaratorinis1 {

    final static String vowels = "aieou";

    public static void main(String[] args) {
        // Užduotis #3 - apsirašome masyvą naudojant vardo ir pavardės ilgius
        String name = "Aurimas";
        String surname = "Šakalys";
        int nameLength = name.length();
        int surnameLength = surname.length();
        int[][] array = new int[nameLength][surnameLength];

        // Užduotis #4 - užpildome šį masyvą intervale apibrėžtais atsitiktiniais dydžiais
        int min = VowelCount(name);
        int max = nameLength + surnameLength;

        {
            Random prng = new Random();
            int to = max - min;
            for (int i = 0; i < nameLength; i++) {
                for (int j = 0; j < surnameLength; j++) {
                    array[i][j] = prng.nextInt(to) + min;
                }
            }
            Print2DIntArray(array, nameLength, surnameLength);
        }
    }

    public static int VowelCount(String target) {
        int total = 0;
        for (char c : target.toLowerCase().toCharArray()) {
            if(vowels.indexOf(c) >= 0){
                total++;
            }
        }
        return total;
    }

    public static void Print2DIntArray(int[][] array, int rowCount, int columnCount){
        for (int i = -1; i < rowCount; i++) {
            for (int j = -1; j < columnCount; j++) {
                if(i == -1 && j == -1) System.out.print('\t');
                else if (i == -1) System.out.print(j + "\t");
                else if (j == -1) System.out.print(i + "\t");
                else System.out.print(array[i][j] + "\t");
            }
            System.out.print('\n');
        }
    }
}
