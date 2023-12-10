package algonquin.cst2335.dictionaryapp;
public class DictionaryItem {
    private String word;
    private String Defination;

    public DictionaryItem(String word, String meaning) {
        this.word = word;
        this.Defination = meaning;
    }

    public String getWord() {
        return word;
    }

    public String getMeaning() {
        return Defination;
    }
}

