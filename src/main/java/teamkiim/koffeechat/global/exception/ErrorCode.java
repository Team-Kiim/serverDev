package teamkiim.koffeechat.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 400 BAD REQUEST
    EMAIL_PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "이메일, 비밀번호가 일치하지 않습니다"),
    AUTH_CODE_NOT_MATCH(HttpStatus.BAD_REQUEST, "인증 코드가 일치하지 않습니다."),

    POST_REQUEST_WITHOUT_TITLE(HttpStatus.BAD_REQUEST, "제목을 입력해주세요"),
    POST_REQUEST_WITHOUT_CONTENT(HttpStatus.BAD_REQUEST, "내용을 입력해주세요"),

    FILE_REQUEST_ERROR(HttpStatus.BAD_REQUEST, "파일 요청이 올바르지 않습니다."),

    // 401 UNAUTHORIZED
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증 정보가 없습니다. 다시 로그인 하세요"),

    // 403 FORBIDDEN
    FORBIDDEN(HttpStatus.FORBIDDEN, "해당 회원에 권한이 없습니다."),
    VOTE_FORBIDDEN(HttpStatus.FORBIDDEN, "해당 회원에 투표 생성 권한이 없습니다."),

    // 404 NOT FOUND
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 회원이 존재하지 않습니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 게시글이 존재하지 않습니다."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 댓글이 존재하지 않습니다."),
    FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 파일입니다."),
    CHATROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "채팅방을 찾을 수 없습니다"),
    LIKE_NOT_FOUND(HttpStatus.NOT_FOUND, "좋아요가 존재하지 않습니다."),
    BOOKMARK_NOT_FOUND(HttpStatus.NOT_FOUND, "북마크가 존재하지 않습니다."),
    MEMBER_FOLLOW_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER FOLLOW가 존재하지 않습니다."),
    VOTE_NOT_FOUND(HttpStatus.NOT_FOUND, "투표가 존재하지 않습니다."),
    VOTE_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND,"투표 항목이 존재하지 않습니다."),
    CORP_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 회사 도메인이 존재하지 않습니다."),

    // 409 CONFLICT
    EMAIL_ALREADY_EXIST(HttpStatus.CONFLICT, "이미 회원가입된 이메일입니다."),
    CHAT_ROOM_ALREADY_EXIST(HttpStatus.CONFLICT, "이미 채팅방이 존재합니다."),
    VOTE_ALREADY_EXIST(HttpStatus.CONFLICT, "이미 투표되었습니다."),
    CORP_ALREADY_EXIST(HttpStatus.CONFLICT, "이미 존재하는 회사 도메인입니다."),

    // 500 INTERNAL_SERVER_ERROR
    JSON_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "소셜 로그인 사용자 정보 json 파싱 에러"),
    CANNOT_SEND_EMAIL(HttpStatus.INTERNAL_SERVER_ERROR, "이메일 전송에 실패했습니다. 이메일이 올바른지 확인해주세요"),
    FILE_IO_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "파일 저장에 실패했습니다.");

    private final HttpStatus httpStatus;
    private final String msg;
}
