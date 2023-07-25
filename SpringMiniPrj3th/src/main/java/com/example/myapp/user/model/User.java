package com.example.myapp.user.model;

import java.sql.Date;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class User {
	@NotNull
	@Pattern(regexp = "^[a-zA-Z]{1}[a-zA-Z0-9_]{4,11}$", message = " 시작은 영문으로만, '_'를 제외한 특수문자 안되며 영문, 숫자, '_'으로만 이루어진 5 ~ 12자 이하의 아이디를 입력해주세요.")
	private String userId;

	@NotNull(message = "비밀번호를 입력해주세요.")
	@Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,}", message = "비밀번호는 최소 6자 이상, 대문자, 소문자, 숫자를 포함해야 합니다.")
	private String userPwd;

	@NotNull(message = "이름을 입력해주세요.")
	private String userName;

	@NotNull(message = "생년월일을 입력해주세요.")
	private Date userBirth;

	@NotNull(message = "이메일을 입력해주세요.")
	@Pattern(regexp = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$", message = "이메일 형식이 올바르지 않습니다.")
	private String userEmail;

	@NotNull(message = "주소를 입력해주세요.")
	private String userAddress;

	private String userDetailAddress;

	@NotNull(message = "전화번호를 입력해주세요.")
	@Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "전화번호 형식에 맞게 입력해주세요 (010-xxxx-xxxx).")
	private String userPhone;

	private int userState;
}
