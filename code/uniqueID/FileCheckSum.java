import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileCheckSum {
    //Hashing Algorithms
    public static final String MD5="MD5"; 
    public static final String SHA1="SHA-1";
    //Parameter options for cmd
    //-f file list
    //-a hashing algorithm
    public static final String FILE="f";
    public static final String ALGORITHM="a";


    /**
     * @param fileName
     * @param algorithm
     * @return
     **/
    public static String generateCheckSum(String fileName,String algorithm) throws NoSuchAlgorithmException, IOException{
        // Create checksum for this file
        File file = new File(fileName);

        // Use MD5 or SAH1 algorithm
        MessageDigest md5Digest = MessageDigest.getInstance(algorithm);

        // Get the checksum
        String checksum = getFileChecksum(md5Digest, file);

        // see checksum
        // System.out.println(checksum);
        return checksum;
    }
    
    private static String getFileChecksum(MessageDigest digest, File file) throws IOException{
        
      //New file input stream for reading the file content
      FileInputStream fis = new FileInputStream(file);
       
      //Create byte array to read data in chunks
      byte[] byteArray = new byte[1024];
      int bytesCount = 0; 
        
      //Read file data and update in message digest
      while ((bytesCount = fis.read(byteArray)) != -1) {
        digest.update(byteArray, 0, bytesCount);
      };
       
      //close the stream; We don't need it now.
      fis.close();
       
      //Get the hash's bytes
      byte[] bytes = digest.digest();
       
      //This bytes[] has bytes in decimal format;
      //Convert it to hexadecimal format
      StringBuilder sb = new StringBuilder();
      for(int i=0; i< bytes.length ;i++)
      {
        sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
      }
       
      //return complete hash
       return sb.toString();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        final Map<String, List<String>> params = new HashMap<>();

        List<String> options = null;
        for (int i = 0; i < args.length; i++) {
            final String a = args[i];
        
            if (a.charAt(0) == '-') {
                if (a.length() < 2) {
                    System.err.println("Error at argument " + a);
                    return;
                }
        
                options = new ArrayList<>();
                params.put(a.substring(1), options);
            }
            else if (options != null) {
                options.add(a);
            }
            else {
                System.err.println("Illegal parameter usage");
                return;
            }
        }        
       
        try{
            String algorithm=MD5;
            long startTime=0;
            long endTime=0;
            String checkSum;
            if(params.get(ALGORITHM) !=null){
               algorithm=params.get(ALGORITHM).get(0);
               if (!MD5.equals(algorithm) & !SHA1.equals(algorithm) ){
                 //use default SHA1
                 System.out.println("Invalid algorithm parameter. Using default: SHA-1 algorithm.");
                 algorithm=SHA1;
               }
            }   
            //add multiple files support using iterator
            List<String> files=params.get(FILE);
            if(files==null){
                System.out.println("Use -f [filename]");
                return;
            }
            for (String fname : files) {
                startTime = System.nanoTime();
                checkSum=FileCheckSum.generateCheckSum(fname,algorithm);
                endTime = System.nanoTime();
                System.out.println("["+((endTime-startTime)/1000000)+"] File: "+fname+" CheckSum: "+checkSum);
            }
        } catch (IOException e) {
            System.err.println(e);
        } catch (NoSuchAlgorithmException e){
            System.err.println(e);
        } catch (Exception e){
            System.err.println(e);
        }
    }

}
