package com.example.demo1;

import java.io.*;
import java.nio.file.*;
import java.util.Base64;
import java.util.Scanner;
import javax.crypto.*;
import javax.crypto.spec.*;

    public class des_ecb_pkcs5_padding {
        public static void main(String[] args) {
            Scanner sc=new Scanner(System.in);
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

                // Ask user for file path and DES key
                System.out.print("Enter the file path: ");
                //We want file path here to encrypt
                String filePath = reader.readLine();

                System.out.print("Enter the DES key (8 characters): ");
                //key to do encryption
                String key = reader.readLine();

                // Encrypt the file
                System.out.println("Encrypt : 1\nDecrypt : 2");
                int a=sc.nextInt();
                switch (a){
                    case 1:  String encryptedFilePath = encryptFile(filePath, key);
                        System.out.println("File encrypted successfully. Encrypted file: " + encryptedFilePath);
                        break;
                    case 2: String decryptedFilePath = decryptFile(filePath, key);
                        System.out.println("File decrypted successfully. Decrypted file: " + decryptedFilePath);
                }


                // Decrypt the file

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //encryption work done here
        public static String encryptFile(String filePath, String key) {
            try {

                System.out.println(filePath + " : " + key);

                SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "DES");
                Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);

                Path inputPath = Paths.get(filePath);
                byte[] fileBytes = Files.readAllBytes(inputPath);
                byte[] encryptedBytes = cipher.doFinal(fileBytes);

                String encryptedcontent= Base64.getEncoder().encodeToString(encryptedBytes);
                Files.write(Paths.get(filePath),encryptedBytes,StandardOpenOption.CREATE);
               // String encryptedFilePath = filePath + ".encrypted";
               // Path outputPath = Paths.get(encryptedFilePath);
                //Files.write(outputPath, encryptedBytes);

                return filePath;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        //decryption work done here
        public static String decryptFile(String filePath, String key) {
            try {
                SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "DES");
                Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
                cipher.init(Cipher.DECRYPT_MODE, secretKey);

                Path inputPath = Paths.get(filePath);
                byte[] encryptedBytes = Files.readAllBytes(inputPath);
                byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

               // String decryptedFilePath = filePath.replace(".encrypted", ".decrypted");
                String decryptedcontent=Base64.getEncoder().encodeToString(decryptedBytes);
               // Path outputPath = Paths.get(decryptedFilePath);
                Files.write(Paths.get(filePath), decryptedBytes);

                return filePath;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

