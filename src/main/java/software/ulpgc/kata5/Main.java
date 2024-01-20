package software.ulpgc.kata5;


import spark.Request;
import spark.Response;
import spark.Spark;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Spark.port(8080);
        Spark.get("/factorial", ((request, response) -> new CommandExecutor(request,response).execute(new FactorialCommand())));
    }

    private record CommandExecutor(Request request, Response response) {
        public Object execute(Command command) {
            Command.Output output = command.execute(input());
            return output.result();
        }

        private Command.Input input() {
            return new Command.Input() {
                @Override
                public String get(String Key) {
                    return oneOf(request.params(Key), request.queryParams(Key));
                }

                private String oneOf(String params, String s) {
                    return params != null ? params : s;
                }
            };
        }
    }
}