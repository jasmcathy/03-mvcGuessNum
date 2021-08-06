package Controller;

import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Model.GuessGameLogic;

@WebServlet(urlPatterns = "/GameServlet")
public class GameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		GuessGameLogic gameService = (GuessGameLogic) session.getAttribute("GameService");
		if (gameService == null) {
			gameService = new GuessGameLogic(1, 10);
			session.setAttribute("GameService", gameService);
		}
		String guess = request.getParameter("number");
		int guessNum = Integer.parseInt(guess);
		if (gameService.isCorrectGuess(guessNum)) {
			generateYouWinResponse(request, response);
			session.invalidate();
		} else {
			int remainder = gameService.getRemainder();
			if (remainder > 0) {
				generateTryAgainResponse(request, response, remainder);

			} else {
				generateTooManyGuessesResponse(request, response);
				session.invalidate();
			}
		}
	}

	public void generateTryAgainResponse(HttpServletRequest request, HttpServletResponse response, int remainder)
			throws IOException {
		String guess = request.getParameter("number");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html><head></head><body>");
		out.println("Your guess " + guess + " , and You wrong.</br>");
		out.println("You still have " + remainder + " chances.Try again.</br>");
		out.println("<a href='guess.html'>Gogogo!!</a>");
		out.println("</body></html>");
		out.close();
	}

	public void generateYouWinResponse(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html><head></head><body>");
		out.println("Congratulations , You win! <BR>");
		out.println("**<a href='guess.html'>New Game</a>**");
		out.println("</body></html>");
		out.close();
	}

	public void generateTooManyGuessesResponse(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String guess = request.getParameter("number");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html><head></head><body>");
		out.println("Your guess " + guess + " , and You wrong.</br>");
		out.println("Sorry, You have no chance.</br>");
		out.println("**<a href='guess.html'>New Game</a>**");
		out.println("</body></html>");
		out.close();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	public String getServletInfo() {
		return "Short description";
	}

}
