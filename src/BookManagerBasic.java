import java.util.*;
import java.util.stream.Collectors;

public class BookManagerBasic implements BooksManager{

    private Map<BookDescription, Integer> _bookCounterMap = new HashMap();

    private RentManager _rentManager = new RentManager(_bookCounterMap);


    @Override
    public int addBook(BookDescription bookDescription) {
       return addBook(bookDescription, 1);
    }

    @Override
    public int addBook(BookDescription bookDescription, int count) {
        if(_bookCounterMap.containsKey(bookDescription)){
            var bookCount = _bookCounterMap.get(bookDescription) + count;
            _bookCounterMap.put(bookDescription, bookCount);
            return bookCount;
        }
        _bookCounterMap.put(bookDescription, count);
        return count;
    }

    @Override
    public int getBookAmount(BookDescription book) {
        return _bookCounterMap.getOrDefault(book, 0);
    }

    class BookComparator implements Comparator<Map.Entry<BookDescription, Integer>>{

        public int compare(Map.Entry<BookDescription, Integer> a, Map.Entry<BookDescription, Integer> b){

            return Integer.compare(b.getValue(), a.getValue());
        }
    }

    @Override
    public List<BookDescription> getBooksByCount() {
        return _bookCounterMap
                .entrySet()
                .stream()
                .sorted(new BookComparator())
                .map(entry-> entry.getKey())
                .collect(Collectors.toList());
    }

    @Override
    public boolean rentBook(Client client, BookDescription book) {
        return _rentManager.rentBook(client, book);
    }

    @Override
    public boolean returnBook(Client client, BookDescription book) {
        return _rentManager.returnBook(client, book);
    }

    @Override
    public Collection<BookDescription> getRentedBooks(Client client) {
        return _rentManager.getRentedBooks(client);
    }

    @Override
    public Map<BookDescription, Integer> getRentedBooks() {
        return _rentManager.getRentedBooks();
    }

    @Override
    public Collection<Client> getBookRenters(BookDescription book) {
        return _rentManager.getBookRenters(book);
    }

    @Override
    public List<Client> getRentChampions() {
        return _rentManager.getRentChampions();
    }
}
