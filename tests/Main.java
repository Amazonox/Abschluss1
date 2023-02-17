public class Main {
    public static void main(String[] args) {
        StringBuilder stringBuilder = new StringBuilder("hallo");
        System.out.println(stringBuilder);
        stringBuilder.append(System.lineSeparator());
        System.out.println(stringBuilder);
        stringBuilder.delete(stringBuilder.length()-System.lineSeparator().length(),stringBuilder.length());
        System.out.println(stringBuilder);
    }
}
