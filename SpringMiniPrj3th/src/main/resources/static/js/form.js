function sample6_execDaumPostcode() {
	new daum.Postcode(
		{
			oncomplete: function(data) {
				// 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

				// 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
				var addr = ''; // 주소 변수
				var extraAddr = ''; // 참고항목 변수

				//사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
				if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
					addr = data.roadAddress;
				} else { // 사용자가 지번 주소를 선택했을 경우(J)
					addr = data.jibunAddress;
				}

				// 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
				if (data.userSelectedType === 'R') {
					// 법정동명이 있을 경우 추가한다. (법정리는 제외)
					// 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
					if (data.bname !== ''
						&& /[동|로|가]$/g.test(data.bname)) {
						extraAddr += data.bname;
					}
					// 건물명이 있고, 공동주택일 경우 추가한다.
					if (data.buildingName !== ''
						&& data.apartment === 'Y') {
						extraAddr += (extraAddr !== '' ? ', '
							+ data.buildingName
							: data.buildingName);
					}
					// 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
					if (extraAddr !== '') {
						extraAddr = ' (' + extraAddr + ')';
					}
					// 조합된 참고항목을 해당 필드에 넣는다.
					document.getElementById("sample6_extraAddress").value = extraAddr;

				} else {
					document.getElementById("sample6_extraAddress").value = '';
				}

				// 우편번호와 주소 정보를 해당 필드에 넣는다.
				document.getElementById("sample6_postcode").value = data.zonecode;
				document.getElementById("sample6_address").value = addr;
				// 커서를 상세주소 필드로 이동한다.
				document.getElementById("sample6_detailAddress")
					.focus();
			}
		}).open();
}

// 현재 날짜를 얻기 위한 함수
function getCurrentDate() {
	var today = new Date();
	var year = today.getFullYear();
	var month = String(today.getMonth() + 1).padStart(2, '0');
	var day = String(today.getDate()).padStart(2, '0');
	return year + '-' + month + '-' + day;
}

// 기본값을 오늘 날짜로 설정
var userBirthInput = document.getElementById('user_birth');
userBirthInput.value = getCurrentDate();

// 미래 날짜를 선택하지 못하도록 설정
userBirthInput.addEventListener('input', function() {
	if (this.value > getCurrentDate()) {
		this.value = getCurrentDate();
	}
});

//fetch 함수를 이용한 POST 형식 메일 전송

const mailCheck = document.querySelector("#mail-Check-btn");
const userEmailInput = document.querySelector("#user_email");

mailCheck.addEventListener("click", () => {

	const email = document.querySelector("#user_email").value;
	const user = {
		username: email,
	};

	const url = "/api/mailcheck";
	fetch(url, {
		method: "POST",
		body: JSON.stringify(user),
		headers: {
			"Content-Type": "application/json",
		},
	})
		.then((response) => response.json())
		.then((json) => {
			if (json !== null) {
				alert("인증메일이 전송 되었습니다.");
				authNum = json;
				userEmailInput.setAttribute("disabled", "disabled");
				document.querySelector(".mail-check-input").removeAttribute("disabled");
			} else {
				alert("인증메일 전송에 실패 하였습니다.");
				userEmailInput.removeAttribute("disabled");
			}
		});
});

//인증번호 인증을 담당하는 메소드
function checkAuthNumFn() {
	const mailCheckInput = document.querySelector(".mail-check-input").value;
	const mailCheckWarn = document.getElementById("mail-check-warn");

	console.log(mailCheckInput);
	console.log(mailCheckWarn);
	console.log(authNum);

	if (mailCheckInput != authNum) {
		mailCheckWarn.textContent = "  인증번호가 다릅니다.";
		mailCheckWarn.style.color = "red";
		       userEmailInput.removeAttribute("disabled");
		return;
	} else {
		mailCheckWarn.textContent = "  인증되었습니다.";
		mailCheckWarn.style.color = "blue";
		authResult = true;
		return;
	}
}


/*const joinForm = document.getElementById("joinForm");

joinForm.addEventListener("submit", (event) => {
	event.preventDefault(); // 폼 제출 기본 동작을 막습니다.

	// 유효성 검사 결과를 확인하는 변수
	let isFormValid = true;

	// 각 필수 입력 칸의 값을 가져옵니다.
	const userId = document.getElementById("user_id").value;
	const userPwd = document.getElementById("user_pwd").value;
	const userName = document.getElementById("user_name").value;
	const userBirth = document.getElementById("user_birth").value;
	const userEmail = document.getElementById("user_email").value;
	const userAddress = document.getElementById("sample6_address").value;
	const userPhone = document.getElementById("user_phone").value;

	// 각 입력 칸의 유효성을 확인하고 필요한 조건을 추가할 수 있습니다.
	if (userId.trim() === "") {
		document.getElementById("user_id_error").textContent = "아이디를 입력해주세요.";
		isFormValid = false;
	} else {
		document.getElementById("user_id_error").textContent = ""; // 오류가 없으면 메시지 제거
	}

	if (userPwd.trim() === "") {
		document.getElementById("user_pwd_error").textContent = "비밀번호를 입력해주세요.";
		isFormValid = false;
	} else {
		document.getElementById("user_pwd_error").textContent = ""; // 오류가 없으면 메시지 제거
	}

	if (userName.trim() === "") {
		document.getElementById("user_name_error").textContent = "이름을 입력해주세요.";
		isFormValid = false;
	} else {
		document.getElementById("user_name_error").textContent = ""; // 오류가 없으면 메시지 제거
	}

	if (userBirth.trim() === "") {
		document.getElementById("user_birth_error").textContent = "생년월일을 입력해주세요.";
		isFormValid = false;
	} else {
		document.getElementById("user_birth_error").textContent = ""; // 오류가 없으면 메시지 제거
	}

	if (userEmail.trim() === "") {
		document.getElementById("user_email_error").textContent = "이메일을 입력해주세요.";
		isFormValid = false;
	} else {
		document.getElementById("user_email_error").textContent = ""; // 오류가 없으면 메시지 제거
	}

	if (userAddress.trim() === "") {
		document.getElementById("user_address_error").textContent = "주소를 입력해주세요.";
		isFormValid = false;
	} else {
		document.getElementById("user_address_error").textContent = ""; // 오류가 없으면 메시지 제거
	}

	if (userPhone.trim() === "") {
		document.getElementById("user_phone_error").textContent = "전화번호를 입력해주세요.";
		isFormValid = false;
	} else {
		document.getElementById("user_phone_error").textContent = ""; // 오류가 없으면 메시지 제거
	}

	// 모든 필수 입력 칸이 유효한 경우에만 회원가입 처리를 진행합니다.
	if (isFormValid && authResult) {
		alert("회원가입이 완료되었습니다.");
		joinForm.submit(); // 폼을 서버로 제출합니다.
	} else if (!authResult) {
		alert("이메일 인증이 필요합니다. 인증을 완료해주세요.");
	}
});*/