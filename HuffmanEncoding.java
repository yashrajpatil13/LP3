import java.util.*;

public class HuffmanEncoding {
    // Node class for Huffman Tree
    static class HuffmanNode implements Comparable<HuffmanNode> {
        char character;
        int frequency;
        HuffmanNode left;
        HuffmanNode right;
        
        public HuffmanNode(char character, int frequency) {
            this.character = character;
            this.frequency = frequency;
            this.left = null;
            this.right = null;
        }
        
        @Override
        public int compareTo(HuffmanNode other) {
            return this.frequency - other.frequency;
        }
    }
    
    // Class to store character details
    static class CharacterData {
        char character;
        int frequency;
        String code;
        
        public CharacterData(char character, int frequency, String code) {
            this.character = character;
            this.frequency = frequency;
            this.code = code;
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Input message
        System.out.println("Enter the message to encode:");
        String message = scanner.nextLine();
        
        if (message.isEmpty()) {
            System.out.println("Message cannot be empty!");
            return;
        }
        
        // Count frequency of each character
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char c : message.toCharArray()) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }
        
        // Create priority queue and add nodes
        PriorityQueue<HuffmanNode> pq = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            pq.add(new HuffmanNode(entry.getKey(), entry.getValue()));
        }
        
        // Build Huffman Tree
        HuffmanNode root = buildHuffmanTree(pq);
        
        // Generate Huffman codes
        Map<Character, String> huffmanCodes = new HashMap<>();
        generateCodes(root, "", huffmanCodes);
        
        // Create list of character data for output
        List<CharacterData> characterDataList = new ArrayList<>();
        for (Map.Entry<Character, String> entry : huffmanCodes.entrySet()) {
            char c = entry.getKey();
            characterDataList.add(new CharacterData(
                c,
                frequencyMap.get(c),
                entry.getValue()
            ));
        }
        
        // Sort by frequency in descending order
        characterDataList.sort((a, b) -> b.frequency - a.frequency);
        
        // Calculate statistics
        int encodedLength = calculateEncodedLength(message, huffmanCodes);
        int variableCodeLength = calculateVariableCodeLength(characterDataList);
        int tableLength = (8 * characterDataList.size()) + variableCodeLength; // 8 bits per ASCII character + variable code bits
        int totalCompressedLength = encodedLength + tableLength;
        
        // Print results
        System.out.println("\nHuffman Encoding Results:");
        System.out.println("----------------------------------------");
        System.out.printf("%-15s %-15s %-15s %-15s%n", 
            "Character", "Frequency", "Code", "Bits in Msg");
        System.out.println("----------------------------------------");
        
        for (CharacterData data : characterDataList) {
            System.out.printf("%-15s %-15d %-15s %-15d%n",
                "'" + data.character + "'",
                data.frequency,
                data.code,
                data.frequency * data.code.length());
        }
        
        System.out.println("\nCompression Details:");
        System.out.println("----------------------------------------");
        System.out.printf("Encoded Message Length: %d bits%n", encodedLength);
        System.out.printf("ASCII Table Length: %d bits%n", 8 * characterDataList.size());
        System.out.printf("Variable Code Length: %d bits%n", variableCodeLength);
        System.out.printf("Total Table Length: %d bits%n", tableLength);
        System.out.printf("Total Compressed Length: %d bits%n", totalCompressedLength);
        
        // Print encoded message
        System.out.println("\nEncoded Message:");
        System.out.println(encodeMessage(message, huffmanCodes));
        
        scanner.close();
    }
    
    // Build Huffman Tree using priority queue
    private static HuffmanNode buildHuffmanTree(PriorityQueue<HuffmanNode> pq) {
        while (pq.size() > 1) {
            HuffmanNode left = pq.poll();
            HuffmanNode right = pq.poll();
            HuffmanNode internal = new HuffmanNode('\0', left.frequency + right.frequency);
            internal.left = left;
            internal.right = right;
            pq.add(internal);
        }
        return pq.poll();
    }
    
    // Generate Huffman codes recursively
    private static void generateCodes(HuffmanNode root, String code, 
                                    Map<Character, String> huffmanCodes) {
        if (root == null) return;
        if (root.left == null && root.right == null) {
            huffmanCodes.put(root.character, code);
        }
        generateCodes(root.left, code + "0", huffmanCodes);
        generateCodes(root.right, code + "1", huffmanCodes);
    }
    
    // Calculate length of encoded message
    private static int calculateEncodedLength(String message, Map<Character, String> codes) {
        int length = 0;
        for (char c : message.toCharArray()) {
            length += codes.get(c).length();
        }
        return length;
    }
    
    // Calculate total length of variable-length codes
    private static int calculateVariableCodeLength(List<CharacterData> characterDataList) {
        int totalCodeBits = 0;
        for (CharacterData data : characterDataList) {
            totalCodeBits += data.code.length();
        }
        return totalCodeBits;
    }
    
    // Encode message using Huffman codes
    private static String encodeMessage(String message, Map<Character, String> codes) {
        StringBuilder encoded = new StringBuilder();
        for (char c : message.toCharArray()) {
            encoded.append(codes.get(c));
        }
        return encoded.toString();
    }
}