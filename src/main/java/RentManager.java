import java.util.*;
import java.util.stream.Collectors;

public class RentManager {
    private Map<BookDescription, List<Client>> _bookDescriptionClient = new HashMap();
    private Map<Client, List<BookDescription>> _clientBookDescription = new HashMap();
    private Map<BookDescription, Integer> bookCounterMap;


    public RentManager(Map<BookDescription, Integer> bookCounterMap) {
        this.bookCounterMap = bookCounterMap;
    }

    public boolean rentBook(Client client, BookDescription book) {
        if(!bookCounterMap.containsKey(book)){
            return false;
        }

        var bookCount = bookCounterMap.get(book);

        var rentedBooksCount = GetBookRentersCount(book);

        if(rentedBooksCount >= bookCount){
            return false;
        }

        Add(book, client);

        return true;
    }

    public boolean returnBook(Client client, BookDescription book){
        if(!IsClientRentedBook(client, book)){
            return false;
        }
        Remove(client, book);
        return true;
    }

    public Collection<BookDescription> getRentedBooks(Client client){
        if(!_clientBookDescription.containsKey(client)){
            return new ArrayList<>();
        }
        return _clientBookDescription.get(client);
    }

    public Map<BookDescription, Integer> getRentedBooks(){
        return _bookDescriptionClient
                .entrySet()
                .stream()
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                e -> e.getValue().size()));
    }

    public Collection<Client> getBookRenters(BookDescription book){
        if(!_bookDescriptionClient.containsKey(book)){
            return new ArrayList<>();
        }
        return _bookDescriptionClient.get(book);
    }

    public List<Client> getRentChampions(){
        return _clientBookDescription
                .entrySet()
                .stream()
                .map(e ->
                        new AbstractMap.SimpleEntry<>(
                                e.getKey(),
                                e.getValue().size()))
                .sorted(new ClientComparator())
                .map(e-> e.getKey())
                .limit(3)
                .collect(Collectors.toList());
    }

    class ClientComparator implements Comparator<Map.Entry<Client, Integer>>{

        public int compare(Map.Entry<Client, Integer> a, Map.Entry<Client, Integer> b){

            return Integer.compare(b.getValue(), a.getValue());
        }
    }

    private int GetBookRentersCount(BookDescription book)
    {
        var clientList = _bookDescriptionClient.get(book);
        if(clientList == null) {
            return 0;
        }
        return clientList.size();
    }

    private boolean IsClientRentedBook(Client client, BookDescription book){
        var bookList = _clientBookDescription.get(client);
        if(bookList == null){
            return false;
        }
        return bookList.contains(book);
    }

    private void Add(BookDescription book, Client client)
    {
        var clientList = _bookDescriptionClient.get(book);
        if(clientList == null) {
            clientList = new ArrayList<>();
            _bookDescriptionClient.put(book, clientList);
        }
        clientList.add(client);


        var bookList = _clientBookDescription.get(client);
        if(bookList == null) {
            bookList = new ArrayList<>();
            _clientBookDescription.put(client, bookList);
        }
        bookList.add(book);
    }

    private void Remove(Client client, BookDescription book){
        var bookList = _clientBookDescription.get(client);
        bookList.remove(book);


        var clientList = _bookDescriptionClient.get(book);
        clientList.remove(client);
    }
}
