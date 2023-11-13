package com.example.demo1;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class AES_CBC_No_Padding {
    public static void main(String[] args) {
        try {
            String filepath = "D://practice//abc5.mkv";
            String key = "1234567890123456";

            // Encryption
            encryption(filepath, key);

            // Decryption
            decryption(filepath, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void encryption(String filepath, String key) {
        try {
            Path inputfile = Paths.get(filepath);
            byte file_byte_data[] = Files.readAllBytes(inputfile);

            // Pad the input data to make its length a multiple of 16
            int blockSize = 16;
            int padding = blockSize - (file_byte_data.length % blockSize);
            byte[] paddedData = new byte[file_byte_data.length + padding];
            System.arraycopy(file_byte_data, 0, paddedData, 0, file_byte_data.length);

            // Generate a random IV
            byte[] ivBytes = new byte[blockSize];
            // You might want to use a secure method to generate a random IV
            // For simplicity, this example uses zeros as the IV
            Arrays.fill(ivBytes, (byte) 0);

            // Encrypt the padded data
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(ivBytes));
            byte encryption[] = cipher.doFinal(paddedData);

            // Write the encrypted data (including IV) to the file
            Files.write(inputfile, concatenateArrays(ivBytes, encryption));
            System.out.println("Successfully Encrypted.....");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void decryption(String filepath, String key) {
        try {
            Path inputfile = Paths.get(filepath);
            byte file_byte_data[] = Files.readAllBytes(inputfile);

            // Extract the IV from the encrypted data (first 16 bytes)
            byte[] ivBytes = Arrays.copyOfRange(file_byte_data, 0, 16);

            // Decrypt the data (excluding the IV)
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(ivBytes));
            byte decryptedData[] = cipher.doFinal(file_byte_data, 16, file_byte_data.length - 16);

            // Remove the padding from the decrypted data
            int paddingLength = 0;
            for (int i = decryptedData.length - 1; i >= 0; i--) {
                if (decryptedData[i] == 0) {
                    paddingLength++;
                } else {
                    break;
                }
            }

            // Create a new array without the padding
            byte[] originalData = new byte[decryptedData.length - paddingLength];
            System.arraycopy(decryptedData, 0, originalData, 0, originalData.length);

            // Write the decrypted data to the file
            Files.write(inputfile, originalData);
            System.out.println("Successfully Decrypted.....");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static byte[] concatenateArrays(byte[] a, byte[] b) {
        byte[] result = new byte[a.length + b.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }
}

