package com.ldu.spring_blogcrud.common.exceptions;

import lombok.Getter;
import org.aspectj.weaver.ast.Not;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // 작은 프로젝트에서 에러코드가 따로 필요한가?

/*  200 OK - 요청 성공
    201 Created - 요청에 따른 새로운 리소스 생성 성공
    204 No Content - 요청은 성공했지만 딱히 보내줄 내용이 없음
    400 Bad Request - 잘못된 요청
    401 Unauthorized - 비인증 요청
    403 Forbidden - 비승인 요청
    404 Not Found - 존재하지 않는 리소스에 대한 요청
    500 Internal Server Error - 서버 에러
    503 Service Unavailable - 서비스가 이용 불가능함*/

    // 서버에러
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "C_001", "서버가 터졌습니다."),
    NOT_FOUND(404,"C-002","PAGE NOT FOUND"),
    // 적절하지 않은 요청값.
    INVALID_INPUT_VALUE(400, "C_003", "적절하지 않은 요청 값입니다."),
    // ID 찾을 수 없을 때?
    ENTITY_NOT_FOUND(400, "C_004", "지정한 Entity를 찾을 수 없습니다."),

    // 회원 가입 과정
    NOT_MATCH_REGEX(403, "AU_001", "정규 표현식이 맞지 않습니다."),
    BAD_CHECK(400, "AU_002", "잘못된 패스워드입니다."),
    NICKNAME_DUPLICATED(400, "AU_003", "중복된 이름을 추가할 수 없습니다."),

    // 비밀번호?!
    POST_UNAUTHORIZED(403, "UC_001", "게시글을 수정할 수 있는 권한이 없습니다."),
    DELETE_UNAUTHORIZED(403, "UC_002", "게시글을 삭제할 수 있는 권한이 없습니다.");

    private final int status;
    private final String code;
    private final String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
