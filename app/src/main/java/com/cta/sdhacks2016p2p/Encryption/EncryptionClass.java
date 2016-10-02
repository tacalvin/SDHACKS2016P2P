import java.lang.*;
import java.math.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

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
        private char[] pRanStr;
        private int hamming;


        //1 Generate pseudorandom string X = (x1,...,xk) with hamming weight h such that v = k/h be an integer
        //hamming weight h such that v = k/h be an integer


        //2 Computes ~m = (d1 || ... || dh)


        //3 Using pesudorandom string X=(x1,...,xk) performs a random permutation on the message blocks di,
        //1 <= i <= h and pad some confuse data blocks to them such that



    }





}
