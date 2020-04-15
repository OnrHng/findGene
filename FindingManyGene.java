import edu.duke.*;
/**
 * Write a description of FindingManyGene here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class FindingManyGene {
    public String findGene(String dna, int where){
        int startIndex = dna.indexOf("ATG", where);
        if (startIndex == -1){
            return "";
        }
        int currStopCodon = 0;
        int stopCodonTAA = findStopCodon(dna, startIndex, "TAA");
        int stopCodonTAG = findStopCodon(dna, startIndex, "TAG");
        int stopCodonTGA = findStopCodon(dna, startIndex, "TGA");
        if (stopCodonTAA == -1 || (stopCodonTAG != -1 && stopCodonTAG <
            stopCodonTAA)){
                currStopCodon = stopCodonTAG;
            }
        else {
            currStopCodon = stopCodonTAA;
        }
        if (currStopCodon == -1 || (stopCodonTGA != -1 && stopCodonTGA < currStopCodon)){
            currStopCodon = stopCodonTGA;
        }
        if (currStopCodon == -1) {
            return "";
        }
        return dna.substring(startIndex, currStopCodon + 3);
    }
    public int findStopCodon(String dna, int startIndex, String stopCodon){
        int indexStopCodon = dna.indexOf(stopCodon, startIndex+3);
        while (indexStopCodon != -1) {
            if ((indexStopCodon - startIndex) % 3 == 0){
               return indexStopCodon; 
            }else { 
               indexStopCodon = dna.indexOf(stopCodon, indexStopCodon + 1);
            }
        }
        return -1;
    }
    public void testFindStopCodon(){
        String dna1 = "TTTATGCDGGATTAAABC";
        System.out.println("dna 1 with TAA: " + findStopCodon(dna1, 0, "TAA"));
        String dna2 = "TTTATGCDGGATDSATAGTAAABC";
        System.out.println("dna 2 with TAG: " + findStopCodon(dna2, 0, "TAG"));
        String dna3 = "TTTATGCDGGATDSATAATGATAGTAAABC";
        System.out.println("dna 3 with TGA: " + findStopCodon(dna3, 0, "TGA"));
        String dna4 = "TTTATGCDGGATDSAASDDASABC";
        System.out.println("dna 4 with TGA: " + findStopCodon(dna4, 0, "TGA"));
    }
    public void testFindingGene(){
        String dna1 = "TTTATGCDGGATTAAABC";
        System.out.println("dna 1 with TAA: " + findGene(dna1,0));
        String dna2 = "TTTATGCDGGATDSATAATAGTAAABC";
        System.out.println("dna 2 with TAG: " + findGene(dna2,0));
        String dna3 = "TTTATGCDGGATDSATAGTGATAGTAAABC";
        System.out.println("dna 3 with TGA: " + findGene(dna3,0));
        String dna4 = "TTTATGCDGGATDSAASDDASABC";
        System.out.println("dna 4 with no Stop Codon: " + findGene(dna4,0));
        String dna5 = "TTTCDGGATDSAASDDASABC";
        System.out.println("dna 5 with not ATG: " + findGene(dna5,0));
        String dna6 = "TTTATGCDGGATDSAASDDASABC";
        System.out.println("dna 6 with ATG but not stopCodon: " + findGene(dna6,0));
    }
    public void allGene(String dna){
        int startIndex = 0;
        while (true) {
            String gene = findGene(dna, startIndex);
            if (gene.isEmpty()) {
                break;
            }
            System.out.println(gene);
            startIndex = dna.indexOf(gene, startIndex) + gene.length();
        }
    }
    public void testAllGene(){
        String moreDna = "TTTATGCDGGATDSATAATAGTAAABCTTTATGCDGGATTAAABC";
        System.out.println("more gene: ");
        allGene(moreDna);
        String noDna1 = "TTTCDGGATDSAASDDASABC";
        System.out.println("no gene: ");
        allGene(noDna1);
        String noDna2 = "TTTATGCDGGATDSAASDDASABCTTTATGCDGGATTAAABC";
        System.out.println("just one gene: ");
        allGene(noDna2);
        
    }
    public StorageResource getAllGene(String dna){
        StorageResource list = new StorageResource();
        int startIndex = 0;
        while (true) {
            String gene = findGene(dna, startIndex);
            if (gene.isEmpty()) {
                break;
            }
            list.add(gene);
            startIndex = dna.indexOf(gene, startIndex) + gene.length();
        }
        return list;
    }
    public void testStoraGene(){
        FileResource fr = new FileResource("GRch38dnapart.fa");
        String dna = fr.asString();
        String dnaUpper = dna.toUpperCase();
        StorageResource geneList = getAllGene(dna);
        for (String g : geneList.data()){
            System.out.println(g);
        }
    }
    public void processGenes(StorageResource sr) {
       
        int data9 = 0;
        double cgRatio = 0.35;
        int srCG = 0;
        for (String s: sr.data()){
            if (s.length() > 60){
                data9 = data9 + 1;
                System.out.println(" greater then 60: " + s);
                
            }
            System.out.println("number of greater then 60: " + data9);
            int indexC = s.indexOf("C");
            int countC = 0;
            int indexG = s.indexOf("G");
            int countG = 0;
            while (indexC != -1) {           
                countC = countC + 1;
                indexC = s.indexOf("C", indexC + 1);
            }
            
            while(indexG != -1){
                countG = countG + 1;
                indexG = s.indexOf("G", indexG + 1);
            }
            double totalCG = countC + countG;
            System.out.println("total sum CG : " + totalCG);
            int length = s.length();
            System.out.println("s length : " + length);
            double ratioCG = 0.0;
            ratioCG = totalCG / length;
            System.out.println("total CG ratio : " + ratioCG);
            if (ratioCG > cgRatio){
                srCG = srCG + 1;
               System.out.println("higher than 0.35 CGratio : " + ratioCG);
            }
            System.out.println("string number Whose has CG ratio higher 0.35: " + srCG);
        }
    
    }
    public void testProcessGene(){
        FileResource fr = new FileResource("GRch38dnapart.fa");
        String dna = fr.asString();
        String dnaUpper = dna.toUpperCase();
        System.out.println(dnaUpper);
        processGenes(getAllGene(dnaUpper));
        
                            
    }
    public void testCTGCount(){
        FileResource fr = new FileResource("GRch38dnapart.fa");
        String dna = fr.asString();
        String dnaUpper = dna.toUpperCase();
        //System.out.println(dnaUpper);
        int start = 0;
        int CTG = dnaUpper.indexOf("CTG", start);
        int countCTG = 0;
        while (CTG != -1) {
            countCTG += 1;
              CTG = dna.indexOf("CTG", CTG + 1);
            }
        System.out.println("CTG counts : " + countCTG);    
        }
        
               
       
    }

