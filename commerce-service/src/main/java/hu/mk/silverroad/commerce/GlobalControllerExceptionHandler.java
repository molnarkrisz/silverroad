package hu.mk.silverroad.commerce;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import hu.mk.silverroad.commerce.service.partner.PartnerNotFoundException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(PartnerNotFoundException.class)
	public ResponseEntity<ProblemDetail> handlePartnerNotFound(PartnerNotFoundException exception) {
		return problem(HttpStatus.NOT_FOUND, "Partner not found", exception.getMessage());
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ProblemDetail> handleInvalidArgument(IllegalArgumentException exception) {
		return problem(HttpStatus.BAD_REQUEST, "Invalid request", exception.getMessage());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ProblemDetail> handleUnexpectedException(Exception exception) {
		log.error("Unexpected error while processing HTTP request", exception);

		return problem(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", "An unexpected error occurred.");
	}

	private ResponseEntity<ProblemDetail> problem(HttpStatus status, String title, String detail) {
		var body = ProblemDetail.forStatusAndDetail(status, detail);
		body.setTitle(title);

		return ResponseEntity.status(status).body(body);
	}
}
