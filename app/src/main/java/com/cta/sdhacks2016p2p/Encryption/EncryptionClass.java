import java.lang.*;
import java.lang.reflect.Array;
import java.math.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

import static sun.security.krb5.Confounder.intValue;

public class EncryptionClass {

    public EncryptionClass()
    {}

    public class KeyGeneration {
        private int kSize;
        private BigInteger[] cArray; //ck = 1 and ci has (L-i) bits binary length for i = 1, ..., L-i
        private BigInteger[] bArray; //bi = 2^(i-1)*ci for i = 1, ..., k
        private BigInteger sumBi;
        private BigInteger M;  //M > bi
        private BigInteger w;  //0 < w < M && gcd(M, w) = 1
        private BigInteger[] aArray; //ai = bi*w mod M for all i = 1, ..., k

        //Very big primes
        private BigInteger p;
        private BigInteger q;

        private BigInteger n; //p*q
        private BigInteger phiN; // = (p-1)*(q-1)

        private BigInteger e; //1 < e < phi(n) and gcd(phi(n),e) = 1
        private BigInteger d; //1 < d < phi(n), e * d = 1 % phi(n)

        private ArrayList<BigInteger>publicTemp;
        private ArrayList<BigInteger>privateTemp;

        private BigInteger randomWithinRange(int min, int max) {
            BigInteger range = BigInteger.valueOf((max-min)+1);
            return BigInteger.valueOf((long)(Math.random() * range.doubleValue()) + min);
        }

        public BigInteger commputeBi(BigInteger i) {
            int temp = i.intValue();
            return BigInteger.valueOf((long)Math.pow(2, (temp-1)));
        }

        public KeyGeneration(int kSize) {
            this.kSize = kSize;
            cArray = new BigInteger[kSize];
            bArray = new BigInteger[kSize];
            aArray = new BigInteger[kSize];
        }

        public void createKey(ArrayList<BigInteger> privateKey, ArrayList<BigInteger> publicKey) {
            // 1. randomly chooose k odd positive integers c1-ck
            for(int i=0; i<kSize-1; i++) {
                BigInteger tempNum = randomWithinRange(1, Integer.MAX_VALUE);
                while(tempNum.mod(BigInteger.valueOf(2)).compareTo(BigInteger.valueOf(0)) == 0) {
                    tempNum = randomWithinRange(1, Integer.MAX_VALUE);
                }
                cArray[i] = tempNum;
            }

            cArray[kSize-1] = BigInteger.valueOf(1);

            // 2. compute b1
            sumBi = BigInteger.valueOf(0);
            for (int i=0; i<kSize; i++) {
                BigInteger temp = commputeBi(BigInteger.valueOf(i));
                bArray[i] = temp;
                sumBi = temp.add(sumBi);
            }
;
            // 3. randomly choose a modulus
            do {
                M = randomWithinRange(1, Integer.MAX_VALUE);
                w = randomWithinRange(1, Integer.MAX_VALUE);
            } while (M.compareTo(sumBi) != 1 && w.compareTo(BigInteger.valueOf(0)) != 1 &&
             w.compareTo(M) != -1 && M.gcd(w).compareTo(BigInteger.valueOf(1)) != 0);

            for (int i=0; i<kSize; i++) {
                aArray[i] = bArray[i].multiply(w).mod(M);
            }
            do {
                Random rnd1 = new Random();
                p = BigInteger.probablePrime(100, rnd1);

                Random rnd2 = new Random();
                q = BigInteger.probablePrime(100, rnd2);
            } while (p.subtract(BigInteger.valueOf(1)).divide(BigInteger.valueOf(2)).isProbablePrime(1) &&
                    q.subtract(BigInteger.valueOf(1)).divide(BigInteger.valueOf(2)).isProbablePrime(1));

            n = p.multiply(q);
            phiN = (p.subtract(BigInteger.valueOf(1)).multiply(q.subtract(BigInteger.valueOf(1))));

            do {
                 Random rnd = new Random();
                 e = BigInteger.probablePrime(100, rnd);
            } while (e.compareTo(BigInteger.valueOf(1)) > 0 && e.compareTo(phiN) < 0 && phiN.gcd(e) ==  BigInteger.valueOf(1));
            ExtendedEuclid();

            privateTemp = new ArrayList<BigInteger>(Arrays.asList(aArray));
            privateTemp.add(n);
            privateTemp.add(e);

            publicTemp = new ArrayList<BigInteger>(Arrays.asList(bArray));
            publicTemp.add(p);
            publicTemp.add(q);
            publicTemp.add(w);
            publicTemp.add(M);
            privateKey = privateTemp;
            publicKey = publicTemp;
        }


        //e.d = 1 mod phi(N)
        public void ExtendedEuclid() {
            System.out.println("Running ExtendedEuclid");
            do {
                d = (BigInteger.valueOf(1).mod(phiN)).divide(e);
            } while(d.compareTo(BigInteger.valueOf(1)) > 0 && e.compareTo(phiN) < 0);
        }

        public void printKeys() {
            System.out.println("Printing the private key");
            for(BigInteger s : privateTemp)
                System.out.println(s);

            System.out.println("Printing the public key");
            for(BigInteger s : publicTemp)
                System.out.println(s);
        }
    }

    public class Encryption {
        //Sender executes the following steps
        private int kSize;
        private String rString;
        private BigInteger hamming;
        private BigInteger v;
        private BigInteger mHat;
        private BigInteger m;
        private BigInteger y;
        private BigInteger s;
        private String mPrime;
        private BigInteger c1;
        private BigInteger c2;
        private ArrayList<BigInteger> dArray;

        public Encryption() {
        }

        private BigInteger randomWithinRange(int min, int max) {
            BigInteger range = BigInteger.valueOf((max-min)+1);
            return BigInteger.valueOf((long)(Math.random() * range.doubleValue()) + min);
        }

        private String generateBinaryString(String userMessage) {
            StringBuilder stringBuilder = new StringBuilder();
            BigInteger binaryLength = BigInteger.valueOf(userMessage.length() * 8);
            // i love and hate biginteger...
            for (BigInteger i = BigInteger.valueOf(0); i.compareTo(binaryLength) < 1; i.add(BigInteger.valueOf(1))) {
                stringBuilder.append(Math.random() * 1);
            }

            /*
            alternative idea replace with biginteger:
            int resultString = (int) pow(2, userMessage.length()*8)
            return resultString.toInteger();
             */
            return stringBuilder.toString();
        }

        private int hammingDistance(String str, int size) {
            int count = 0;
            int temp = Integer.parseInt(str);
            for (int i = 0; i < size; i++) {
                if (temp < 0) {
                    count++;
                }
                temp = temp << 1;
            }
            return count;
        }


            public void shuffle(String input){
                List<Character> characters = new ArrayList<Character>();
                for(char c:input.toCharArray()){
                    characters.add(c);
                }
                StringBuilder output = new StringBuilder(input.length());
                while(characters.size()!=0){
                    int randPicker = (int)(Math.random()*characters.size());
                    output.append(characters.remove(randPicker));
                }
                System.out.println(output.toString());
            }

        public BigInteger[] encryptMessage (ArrayList<BigInteger> privateKey, String userMessage) {
            do {
                rString = generateBinaryString(userMessage);
                hamming = BigInteger.valueOf(hammingDistance(rString, 16));
            } while (BigInteger.valueOf(userMessage.length()).mod(hamming).compareTo(BigInteger.valueOf(0)) != 0);
            v = BigInteger.valueOf(userMessage.length()).divide(hamming);

            // "Sample message a user may be sending to someone else ,this will be used in visualizing the the encryption process."
            String userBinary = userMessage.getBytes().toString();

            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i < rString.length(); i++) {
                stringBuilder.append((rString.charAt(i) ^ userBinary.charAt(i)));
            }

            mPrime = stringBuilder.toString();
            stringBuilder.setLength(0);
            BigInteger n = privateKey.get(privateKey.size() - 2);
            s = n.subtract(BigInteger.valueOf(userBinary.length())).divide(
                    BigInteger.valueOf(userBinary.length()).subtract(hamming));
            String binary = s.toString(2);
            ArrayList<String> dArray = new ArrayList<>();
            for (int i = 0; i <= hamming.intValue(); i++) {
                dArray.add(rString.substring(i, i + hamming.intValue()));
            }
            int confuseCounter = Integer.parseInt(rString) - hamming.intValue();
            while (confuseCounter > 0) {
                confuseBlock(dArray, privateKey, hamming, v);
                confuseCounter--;
            }
            for (int i = 0; i <= hamming.intValue(); i++) {
                int temp = Integer.parseInt(dArray.get(i));
                temp = s.add(BigInteger.valueOf(temp)).intValue();
                dArray.set(i, Integer.toString(temp));
            }

            for (int i = 0; i <= hamming.intValue(); i++) {
                y = y.add(BigInteger.valueOf(Integer.parseInt(dArray.get(i))));
            }

            c1 = y.pow(privateKey.get(privateKey.size()-1).intValue()).mod(privateKey.get(privateKey.size()-2));
            c2 = BigInteger.ZERO;
            for (int i = 1; i <= rString.length(); i++) {
                // c2 = sum of ai and xi from 1 to k.
                c2 = c2.add(privateKey.get(i).multiply(BigInteger.valueOf(Character.getNumericValue(rString.charAt(i)))));
            }

            return new BigInteger[] {c1, c2};
        }

        public void confuseBlock(ArrayList<String> dArray, ArrayList<BigInteger> privateKey, BigInteger hamming, BigInteger v) {
            int begin = (int) Math.random() * hamming.intValue();
            for (int i = 0; i < v.intValue(); i++) {

            }
        }
    }

    private class Decryption {
        private BigInteger c1;
        private BigInteger c2;

        public Decryption(BigInteger c1, BigInteger c2) {
            this.c1 = c1;
            this.c2 = c2;
        }

        public String decryptMessage(String encryptedMessage, ArrayList<BigInteger> publicKey) {
            return "";
        }
    }



}
