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

let authNum;
let authResult;
const mailCheck = document.querySelector("#mail-check-btn");
const userEmail = document.querySelector("#user_email");
mailCheck.addEventListener("click", () => {
	const email = userEmail.value;
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
		.then((response) => {
			if (response.ok) {
				// 응답을 JSON으로 변환하여 인증번호(authNum)를 얻습니다.
				return response.json(); // Parse response as JSON
			} else if (response.status === 400) {
				// 데이터베이스에 이미 존재하는 이메일인 경우
				alert("이미 존재하는 이메일입니다.");
				throw new Error("이미 존재하는 이메일입니다.");
			} else {
				// 기타 에러 처리
				alert("인증메일 전송에 실패하였습니다.");
				throw new Error("인증메일 전송에 실패하였습니다.");
			}
		})
		.then((data) => {
			// 이제 data 객체 안에 인증번호가 들어있습니다.
			authNum = data; // Assign the received authNum to the variable
			alert("인증메일이 전송 되었습니다.");

			// 이메일 입력 칸을 비활성화 해제
			userEmail.removeAttribute("disabled");
			// 인증번호 입력 칸 활성화
			document.querySelector(".mail-check-input").removeAttribute("disabled");
		})
		.catch((error) => {
			// fetch 중 발생한 오류를 처리합니다.
			console.error("오류:", error);
		});
});
// 인증번호 인증을 담당하는 메소드
function checkAuthNumFn() {
	const mailCheckInput = document.querySelector(".mail-check-input").value;
	const mailCheckWarn = document.getElementById("mail-check-warn");


	if (mailCheckInput != authNum) {
		mailCheckWarn.textContent = "인증번호가 다릅니다.";
		mailCheckWarn.style.color = "red";
		// 이메일 입력 칸을 다시 활성화
		return;
	} else {
		mailCheckWarn.textContent = "인증되었습니다.";
		mailCheckWarn.style.color = "blue";
		authResult = true;
		return;
	}
}


function showErrorMessage(fieldId, message) {
	const errorElement = document.getElementById(fieldId + '_error');
	if (errorElement) {
		errorElement.textContent = message;
		errorElement.style.display = 'block';
	}
}

function hideErrorMessage(fieldId) {
	const errorElement = document.getElementById(fieldId + '_error');
	errorElement.textContent = '';
	errorElement.style.display = 'none';
}

function validateForm() {
	const form = document.getElementById('joinForm');

	// Get form field values
	const userId = form.userId.value;
	const userPwd = form.userPwd.value;
	const userName = form.userName.value;
	const userBirth = form.userBirth.value;
	const userEmail = form.userEmail.value;
	const userAddress = form.userAddress.value;
	const userPhone = form.userPhone.value;

	// Regular expression patterns
	const userIdPattern = /^[a-zA-Z]{1}[a-zA-Z0-9_]{4,11}$/;
	const userPwdPattern = /(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,}/;
	const userEmailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
	const userPhonePattern = /^010-\d{4}-\d{4}$/;

	// Validation checks
	if (!userId.match(userIdPattern)) {
		showErrorMessage('user_id', '아이디는 영문으로 시작하고, 영문, 숫자, 밑줄만 사용 가능하며 5~12자 이하로 입력해주세요.');
		return false;
	} else {
		hideErrorMessage('user_id');
	}

	if (!userPwd.match(userPwdPattern)) {
		showErrorMessage('user_pwd', '비밀번호는 최소 6자 이상, 대문자, 소문자, 숫자를 포함해야 합니다.');
		return false;
	} else {
		hideErrorMessage('user_pwd');
	}

	if (userName.trim() === '') {
		showErrorMessage('user_name', '이름을 입력해주세요.');
		return false;
	} else {
		hideErrorMessage('user_name');
	}
	if (userBirth.trim() === '') {
		showErrorMessage('user_birth', '생년월일을 입력해주세요.');
		return false;
	} else {
		hideErrorMessage('user_birth');
	}

	if (!userEmail.match(userEmailPattern)) {
		showErrorMessage('user_email', '이메일 형식이 올바르지 않습니다.');
		return false;
	} else {
		hideErrorMessage('user_email');
	}

	if (userAddress.trim() === '') {
		showErrorMessage('user_address', '주소를 입력해주세요.');
		return false;
	} else {
		hideErrorMessage('user_address');
	}

	if (!userPhone.match(userPhonePattern)) {
		showErrorMessage('user_phone', '전화번호 형식에 맞게 입력해주세요 (010-xxxx-xxxx).');
		return false;
	} else {
		hideErrorMessage('user_phone');
	}

	// 이메일 인증 여부를 확인합니다.
	if (!authResult) {
		alert('이메일 인증이 필요합니다. 인증을 완료해주세요.');
		return false;
	}

	// 모든 유효성 검사가 통과되면 폼 제출이 허용됩니다.	
	return true;
}

const joinForm = document.getElementById('joinForm');
joinForm.addEventListener('submit', validateForm);
