package vilnius.tech;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.util.Random;
import java.util.Arrays;

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
        Integer[][] array = new Integer[nameLength][surnameLength];

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
            Print2DArray(array, true, NumberFormat.getInstance());
        }

        // Užduotis #5 - apskaičiuoti eilučių bei stulpelių reikšmių vidurkius
        Float[] rowAverages = new Float[nameLength];
        Float[] columnAverages = new Float[surnameLength];

        for(int row = 0; row < nameLength; row++) {
            for(int column = 0; column < surnameLength; column++){
                if(rowAverages[row] == null){
                    rowAverages[row] = 0f;
                }
                rowAverages[row] += array[row][column];
            }
            rowAverages[row] /= surnameLength;
        }

        for(int column = 0; column < surnameLength; column++) {
            for(int row = 0; row < nameLength; row++){
                if(columnAverages[row] == null){
                    columnAverages[row] = 0f;
                }
                columnAverages[column] += array[row][column];
            }
            columnAverages[column] /= nameLength;
        }

        final DecimalFormat decimalFormat = new DecimalFormat("##.0000");
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);

	System.out.println("Rows: ");
        PrintArray(rowAverages, decimalFormat);
	System.out.println("Columns: ");
        PrintArray(columnAverages, decimalFormat);

        // Užduotis #6 - skaičiuojame skaičių kiekį kiekvienoje eilutėje kurių reikšmė didesnis nei tos eilutės vidurkis
        for(int row = 0; row < nameLength; row++) {
            int count = 0;
            for(int column = 0; column < surnameLength; column++){
                if(array[row][column] > rowAverages[row]){
                    count++;
                }
            }
            System.out.println("There are " + count + " numbers in row " + row + " that are larger than the average");
        }
	
	// Task #7 - find max number in 2D array excluding a-th row and column
	System.out.println("Max value excluding row " + min + " and column " + min + " is " + MaxExcluding(array, min, min));
	
	// Task #8 - print a-th row values sorted without mutating the array
	Integer[] arrayCopy = array[min].clone();
	Arrays.sort(arrayCopy);
	PrintArray(arrayCopy, NumberFormat.getInstance());	
	
	// Task #9 - find index of min value in columnAverage array and print the min value in the column of said index.
	int minIndex = IndexOfMin(columnAverages);
	int minValue = array[0][minIndex];
	for(int i = 1; i < nameLength; i++) {
		if(array[i][minIndex] < minValue) {
			minValue = array[i][minIndex];
		}
	}	
	System.out.println(minValue);

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

    public static int IndexOfMin(Float[] array) {
	Float min = array[0];
	int index = 0;
	for(int i = 1; i < array.length; i++){
		if(array[i] < min) {
			min = array[i];
			index = i;
		}
	}
	return index;
    }

    public static Integer MaxExcluding(Integer[][] array, int rowExclude, int columnExclude) {
    	Integer max = -1;	
	for(int row = 0; row < array.length; row++){
    	    for(int col = 0; col < array[row].length; col++){
		if(row == rowExclude || col == columnExclude) {
			continue;
		}
		if(array[row][col] > max){
			max = array[row][col];
		}		
			
	    }	
    	}
	return max;
    }

    public static <T> void PrintArray(T[] array, Format format) {
        for (T item : array) {
            System.out.print(format.format(item) + "\t");
        }
        System.out.print('\n');
    }

    public static <T> void Print2DArray(T[][] array, boolean showIndexes, Format format) {
        for (int i = 0; i < array.length; i++) {
            if(showIndexes) {
                if(i == 0) PrintArrayColumnIndexes(array[i].length);
                PrintArrayRowIndex(i);
            }
            PrintArray(array[i], format);
        }
    }

    private static void PrintArrayColumnIndexes(int count) {
        for (int i = -1; i < count; i++) {
            final String symbol = (i == -1 ? " " : String.valueOf(i));
            System.out.print(symbol + "\t");
        }
        System.out.print('\n');
    }

    private static void PrintArrayRowIndex(int index) {
        System.out.print(index + "\t");
    }
}
