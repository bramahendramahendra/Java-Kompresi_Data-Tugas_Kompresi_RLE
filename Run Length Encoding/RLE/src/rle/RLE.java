/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rle;

/**
 *
 * @author Brama Hendra Mahendra
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;

public class RLE {
    //Rumus RLE
    //Rumus Encoding RLE
    public static String[] encode(String input) {
        StringBuffer data = new StringBuffer();
        int paket_RLE = 0;
        int bytes = 0;
        for (int i = 0; i < input.length(); i++) {
            int loop_RLE = 1;
            while (i+1 < input.length() && input.charAt(i) == input.charAt(i+1)) {
                loop_RLE++;
                i++;
            }
            bytes = bytes + 2;
            data.append(loop_RLE);
            data.append(input.charAt(i));
            paket_RLE++;
        }
        String hasil[] = new String[3];
        hasil[0] = data.toString();
        hasil[1] = Integer.toString(paket_RLE);
        hasil[2] = Integer.toString(bytes);
        return hasil;
    }
    //Rumus Decoding RLE
    public static String decode(String input) {
        StringBuffer data = new StringBuffer();
        Pattern pattern = Pattern.compile("[0-9]+|[a-zA-Z]");
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            int nomer = Integer.parseInt(matcher.group());
            matcher.find();
            while (nomer-- != 0) {
                data.append(matcher.group());
            }
        }
        return data.toString();
    }
    
    //Rumus RLE standart CCITT No.2 dan 4
    //Rumus Encoding RLE
    public static String encode_CCITT(String input) {
        StringBuffer data = new StringBuffer();
        char awalan = '0';
        for (int i = 0; i < input.length(); i++) {
            int loop_RLE = 1;
            while (i+1 < input.length() && input.charAt(i) == input.charAt(i+1)) {
                loop_RLE++;
                i++;
            }
            if ((input.charAt(i) == '0')||(input.charAt(i) == '1')) {
                if ((awalan == '0')&&((input.charAt(i) == '1'))) {
                    data.append("0");
                    data.append(" ");
                    data.append(loop_RLE);
                    data.append(" ");
                    awalan = '1';
                } else {
                    data.append(loop_RLE);
                    data.append(" ");
                    awalan = '1';
                }
            } else {
                return "ERROR";
            }    
        }
        return data.toString();
    }
    //Rumus Decoding RLE
    public static String decode_CCITT(String input) {
        if(input == "ERROR") {
            return "ERROR";
        }
        else {
            StringBuffer data = new StringBuffer();
            char awalan = '0';
            for (int i = 0; i < input.length(); i++) {
                if (input.charAt(i) != ' ') {
                    int loop_RLE = Character.getNumericValue(input.charAt(i));
                    if ((loop_RLE == 0)&&(awalan == '0')) {
                        awalan = '1';
                    } else {
                        if (awalan == '0') {
                            while(loop_RLE > 0) {
                                data.append("0");
                                loop_RLE--;
                            }
                            awalan = '1';
                        } else {
                            while(loop_RLE > 0) {
                                data.append("1");
                                loop_RLE--;
                            }
                            awalan = '0';
                        }
                    }
                }
            }
            return data.toString();
        }
    }
    
    public static String RunLengthEncode(String input) {
        StringBuffer data = new StringBuffer();
        char awalan = '0';
        for (int i = 0; i < input.length(); i++) {
            int loop_RLE = 1;
            while (i+1 < input.length() && input.charAt(i) == input.charAt(i+1)) {
                loop_RLE++;
                i++;
            }
            if ((input.charAt(i) == '0')||(input.charAt(i) == '1')) {
                if ((awalan == '0')&&((input.charAt(i) == '1'))) {
                    if (awalan == '0') {
                        data.append("0");
                        data.append(" ");
                    }
                    awalan = '1';
                } else {
                    if (input.charAt(i) == '0') {
                        data.append(loop_RLE);
                        data.append(" ");
                    }
                    awalan = '1';
                }
            } else {
                return "ERROR";
            }    
        }
        return data.toString();
    }
    
    public static boolean cekKelipatan2(int m) {
        if(m%2 == 0){
            return true;
        } else {
            return false;
        }
    }
    
    public static int rumus_q(int n, int m) {  
        return n/m;
    }
    
    public static String unary_code(int q) {  
        StringBuffer data = new StringBuffer();
        while(q > 0) {
            data.append("1");
            q--;
        }
        data.append("0");
        return data.toString();
    }
    
    public static double codeword_bit_r(int m) {
        return (Math.log(m)/Math.log(2));
    }
    
    public static int biner_r(int n, int q, int m) {
        return (n - q * m);
    }
    
    public static String KonversiBiner(int bit_r, int biner_r){
        StringBuffer data = new StringBuffer();
        int binary[] = new int[bit_r];
        int index = 0;
        while(bit_r > 0) {
            
            if(biner_r > 0) {
                binary[index++] = biner_r%2;
                biner_r = biner_r/2;
            }
            else {
                binary[index++] = 0;
            }
            bit_r--;
        }
        for(int i = index-1;i >= 0;i--){
            data.append(Integer.toString(binary[i]));
        }
        return data.toString();
    }
    
    public static int rumus_S(int m) {
        int hasil_log = (int) codeword_bit_r(m);
        double hasil = Math.pow(2, ++hasil_log);
        int S = (int) hasil - m;
        return (S);
    }
    
    public static String GolumbCode(String RLE, int m) {
        StringBuffer data = new StringBuffer();
        int nilai_n = 1;
        //Kondisi Error
        if(RLE == "ERROR") {
            return "ERROR";
        }
        else {
            //Perulangan menghitung Golumb Code dari RLE
            for (int i = 0; i < RLE.length(); i++) {
                if (RLE.charAt(i) != ' ') {
                    System.out.println("Untuk RLE n ke = " + nilai_n);
                    nilai_n++;
                    
                    int n = Character.getNumericValue(RLE.charAt(i));
                    
                    //Cari Kelipatan 2 atau bukan Kelipatan 2
                    if(cekKelipatan2(m) == true) {
                        //menggunakan rumus golumb code kelipatan 2
                        System.out.println("m = " + m);
                        System.out.println("n = " + n);
                        System.out.println("m merupakan kelipatan 2");
                        int q = rumus_q(n, m);
                        System.out.println("q = " + q);
                        String unary_code = unary_code(q);
                        System.out.println("Unary Code = " + unary_code);
                        int bit_r = (int) codeword_bit_r(m);
                        System.out.println("Panjang codeword bit r = " + bit_r + " bit");
                        int biner_r = biner_r(n,q,m);
                        System.out.println("Representasi biner r = " + biner_r);
                        String code_biner = KonversiBiner(bit_r, biner_r);
                        System.out.println("Maka code Binarynya = " + code_biner);
                        data.append(unary_code);
                        data.append(code_biner);
                        data.append(" ");
                    } else {
                        //menggunakan rumus golumb code bukan kelipatan 2
                        System.out.println("m =" + m);
                        System.out.println("n =" + n);
                        System.out.println("m merupakan bukan kelipatan 2");
                        int S = rumus_S(m);
                        System.out.println("S = " + S);
                        int q = rumus_q(n, m);
                        System.out.println("q = " + q);
                        String unary_code = unary_code(q);
                        System.out.println("Unary Code = " + unary_code);
                        data.append(unary_code);
                        
                        int kondisi_r = biner_r(n,q,m);
                        System.out.println("r = " + kondisi_r);
                        if(kondisi_r>=S) {
                            int bit_r = (int) codeword_bit_r(m)+1;
                            System.out.println("Panjang codeword bit r = " + bit_r + " bit");
                            int biner_r = kondisi_r + S;
                            System.out.println("Representasi biner r = " + biner_r);
                            String code_biner = KonversiBiner(bit_r, biner_r);
                            System.out.println("Maka code Binarynya = " + code_biner);
                            data.append(code_biner);
                        }
                        else{
                            if(kondisi_r==1) {
                                data.append("01");
                            }
                            if(kondisi_r==2) {
                                data.append("10");
                            }
                        }
                        data.append(" ");
                    }
                    System.out.println(" ");
                }
            }
            
            //Return hasil Golum Code
            return data.toString();
        }
    }
    
    public static void main(String[] args) {
        String input;
        int m;
        Scanner in = new Scanner(System.in);
        
        //Dengan rumus RLE
        System.out.println("Dengan menggunakan Rumus RLE ");
        System.out.println("Contoh : aabbc = 2a2b1c ");
        System.out.println("Masukkan String : ");
        input = in.nextLine();
        System.out.println(" ");
        
        String[] hasil = encode(input);
        System.out.println("Hasil Encoding adalah : ");
        System.out.println(hasil[0]);
        System.out.println(hasil[1] + " paket RLE");
        System.out.println(hasil[2] + " bytes");
        System.out.println(" ");
        
        System.out.println("Hasil Decoding adalah : ");
        System.out.println(decode(hasil[0]));
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        
        //Dengan rumus RLE standart CCITT No.2 dan 4
        System.out.println("Dengan menggunakan Rumus RLE standart CCITT No.2 dan 4 ");
        System.out.println("Contoh : 00011 = 3 2 ");
        System.out.println("Masukkan bit pixel : ");
        input = in.nextLine();
        System.out.println(" ");
        
        String hasil1 = encode_CCITT(input);
        System.out.println("Hasil Encoding adalah : ");
        System.out.println(hasil1);
        System.out.println(" ");
        
        System.out.println("Hasil Decoding adalah : ");
        System.out.println(decode_CCITT(hasil1));
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        
        //Dengan rumuns RLE enggunakan Golumb code
        System.out.println("Dengan menggunakan Rumus RLE dengan Golumb Coding ");
        System.out.println("Contoh : kode = 000001001 (contoh imputan) m=5 (contoh inputan parameter dimaan m>0) ");
        System.out.println("Masukkan kode : ");
        input = in.nextLine();
        System.out.println("Masukkan parameter : ");
        m = in.nextInt();
        System.out.println(" ");
        
        String hasil2 = RunLengthEncode(input);
        System.out.println("Hasil Run Length Encode adalah : ");
        System.out.println(input);
        System.out.println(hasil2);
        System.out.println("Parameter : " + m);
        System.out.println("");
        
        hasil2 = GolumbCode(hasil2, m);
        System.out.println(hasil2);
        
    }
    
}
