package model.dto.request;

    public class BookRequest {
        private String title;
        private String author;
        private String isbn;
        private Long price;
        private int stock;

        public BookRequest(String title, String author, String isbn, Long price, int stock) {
            this.title = title;
            this.author = author;
            this.isbn = isbn;
            this.price = price;
            this.stock = stock;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getIsbn() {
            return isbn;
        }

        public void setIsbn(String isbn) {
            this.isbn = isbn;
        }

        public Long getPrice() {
            return price;
        }

        public void setPrice(Long price) {
            this.price = price;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }
    }

