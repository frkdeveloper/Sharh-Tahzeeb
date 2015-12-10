package naveed.com.sharhtahzeeb.model;

/**
 * Creado por Hermosa Programación
 */
public class BookModel {
    private int image_id;
    private String bookName;
    private String volume;

    public BookModel(int image_id, String bookName, String volume) {
        this.image_id = image_id;
        this.bookName = bookName;
        this.volume = volume;
    }

    public String getBookName() {
        return bookName;
    }

    public String getVolume() {
        return volume;
    }

    public int getImage_id() {
        return image_id;
    }
}
