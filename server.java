import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import java.util.HashMap;
import java.util.Map;

public class server {
	public static void main(String[] args) throws IOException {
		HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

		server.createContext("/", new MyHandler());

		server.setExecutor(null); // default executor
		server.start();
		System.out.println("Server is running on port 8080");
	}

	static class MyHandler implements HttpHandler {
		public static Map<String, String> map = null;

		static {
			map = new HashMap<>();
			map.put("/", "main.html");
			map.put("/css/styles.css", "css/styles.css");
			map.put("/css/styles-for-mobile.css", "css/styles-for-mobile.css");
			map.put("/css/styles-lab2.css", "css/styles-lab2.css");

			map.put("/img/2.png", "img/2.png");
			map.put("/img/3.png", "img/3.png");
			map.put("/img/4.png", "img/4.png");
			map.put("/img/5.png", "img/5.png");
			map.put("/img/6.png", "img/6.png");
			map.put("/img/7.png", "img/7.png");
			map.put("/img/8.png", "img/8.png");
			map.put("/img/9.png", "img/9.png");
			map.put("/img/10.png", "img/10.png");
			map.put("/img/11.png", "img/11.png");

			map.put("/img/footer.png", "img/footer.png");
			map.put("/img/header.png", "img/header.png");

			map.put("/img/insta.svg", "img/insta.svg");
			map.put("/img/m-burger.svg", "img/m-burger.svg");
			map.put("/img/map.svg", "img/map.svg");
			map.put("/img/phone.svg", "img/phone.svg");
			map.put("/favicon.svg", "favicon.svg");

			map.put("/img/m-4.png", "img/m-4.png");
			map.put("/img/m-10.png", "img/m-10.png");
			map.put("/img/m-title.png", "img/m-title.png");

			map.put("/img/video.mp4", "img/video.mp4");

			map.put("/js/scripts.js", "js/scripts.js");
			

		}

		@Override
		public void handle(HttpExchange t) throws IOException {
			String path = t.getRequestURI().getPath();
			System.out.println(path);
			if(map.containsKey(path)) {
				servFile(t, map.get(path));
			} 	
			else {
				System.out.printf("path not found [%s] \n", path);
			}

		}

		public void servFile(HttpExchange t, String filePath) throws IOException {
			File file = new File(filePath);

			if (file.exists()) {
				t.sendResponseHeaders(200, file.length());

				OutputStream os = t.getResponseBody();
				FileInputStream fis = new FileInputStream(file);
				byte[] buffer = new byte[1024];
				int bytesRead;
				while ((bytesRead = fis.read(buffer)) != -1) {
					os.write(buffer, 0, bytesRead);
				}
				fis.close();
				os.close();
			} else {
				t.sendResponseHeaders(404, 0);
			}
		}

	}
}
