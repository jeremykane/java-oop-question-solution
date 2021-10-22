
/* 
    This Solution is created by: Jeremy Kane
    GitHub link: https://github.com/jeremykane
*/
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Solution {

    public static void main(String args[]) {
        String input = "Charlie,Zoe,08123123123;Andre,Xavier,08111222333;Charlie,Xyz,08123123123;Jean,Summers,08001001001;";
        PhoneBook phoneBook = new PhoneBook();
        phoneBook.handleInput(input);
        System.out.println(phoneBook.getOutput());
    }
}

class Account {
    private String name;
    private String number;

    Account(String name, String number) {
        this.name = name;
        this.number = number;
    }

    String getName() {
        return name;
    }

    String getNumber() {
        return number;
    }

    String getAccountOutput() {
        return name + " - " + number;
    }
}

class Log extends Account {
    private String message;

    Log(String name, String number, String message) {
        super(name, number);
        this.message = message;
    }

    String getMessage() {
        return message;
    }

    String getOutput() {
        return super.getAccountOutput() + " : " + message;
    }

}

class PhoneBook {
    private Map<String, String> accounts;
    private List<Log> logs;

    PhoneBook() {
        accounts = new LinkedHashMap<>();
        logs = new ArrayList<>();
    }

    void handleInput(String input) {
        List<String> accountPart = Arrays.asList(input.split(";"));
        for (String e : accountPart) {
            String[] parts = e.split(",");
            String name = parts[0] + " " + parts[1];
            String number = parts[2];
            Log log;
            if (numberExist(number)) {
                log = new Log(name, number, "duplicate phone number");
            } else {
                log = new Log(name, number, "insert success");
                accounts.put(log.getNumber(), log.getName());
            }
            logs.add(log);
        }
    }

    boolean numberExist(String number) {
        if (accounts.get(number) == null) {
            return false;
        } else {
            return true;
        }
    }

    void insertAccount(Log log) {
        accounts.put(log.getNumber(), log.getName());
    }

    void insertLog(Log log) {
        logs.add(log);
    }

    void sortBook() {
        List<Entry<String, String>> list = new LinkedList<>(accounts.entrySet());
        Collections.sort(list, new Comparator<Entry<String, String>>() {
            public int compare(Entry<String, String> o1, Entry<String, String> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        Map<String, String> temp = new LinkedHashMap<>();
        for (Map.Entry<String, String> e : list) {
            temp.put(e.getKey(), e.getValue());
        }
        accounts = temp;
    }

    String getOutput() {
        String result = "";
        result += "=== Output START ===\nLog:\n";
        for (Log e : logs) {
            result += e.getOutput() + "\n";
        }
        result += "\nPhone Book:\n";
        sortBook();
        int counter = 1;
        for (String number : accounts.keySet()) {
            result += Integer.toString(counter) + ". " + accounts.get(number) + " - " + number + "\n";
        }
        result += "=== Output END ===";
        return result;
    }
}