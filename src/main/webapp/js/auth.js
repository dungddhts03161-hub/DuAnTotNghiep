const tabButtons = document.querySelectorAll('.tab-btn');
const forms = document.querySelectorAll('.auth-form');
const switchButtons = document.querySelectorAll('[data-switch]');
const toast = document.querySelector('.toast');

function showToast(message) {
  if (!toast) return;
  toast.textContent = message;
  toast.classList.add('show');
  setTimeout(() => toast.classList.remove('show'), 2600);
}

function showTab(tabName, shouldReset = true) {
  tabButtons.forEach((button) => {
    button.classList.toggle('active', button.dataset.tab === tabName);
  });

  forms.forEach((form) => {
    const isActive = form.dataset.form === tabName;
    form.classList.toggle('active', isActive);

    if (shouldReset) {
      form.reset();
    }

    form.querySelectorAll('.error').forEach((error) => (error.textContent = ''));
  });
}

tabButtons.forEach((button) => {
  button.addEventListener('click', () => showTab(button.dataset.tab));
});

switchButtons.forEach((button) => {
  button.addEventListener('click', () => showTab(button.dataset.switch));
});

const params = new URLSearchParams(window.location.search);
const initialMode = window.CELINE_AUTH_MODE || (params.get('mode') === 'register' ? 'register' : 'login');
showTab(initialMode, false);

function setError(input, message) {
  const wrapper = input.closest('label');
  const error = wrapper ? wrapper.querySelector('.error') : null;
  if (error) error.textContent = message;
}

function clearErrors(form) {
  form.querySelectorAll('.error').forEach((error) => (error.textContent = ''));
}

function validateRequired(input, label) {
  if (!input.value.trim()) {
    setError(input, `Vui lòng nhập ${label}.`);
    return false;
  }
  return true;
}

const loginForm = document.getElementById('loginForm');
if (loginForm) {
  loginForm.addEventListener('submit', (event) => {
    const form = event.currentTarget;
    clearErrors(form);

    const identity = form.identity;
    const password = form.password;
    let isValid = true;

    isValid = validateRequired(identity, 'email hoặc số điện thoại') && isValid;
    isValid = validateRequired(password, 'mật khẩu') && isValid;

    if (password.value && password.value.length < 6) {
      setError(password, 'Mật khẩu cần tối thiểu 6 ký tự.');
      isValid = false;
    }

    if (!isValid) {
      event.preventDefault();
      showToast('Vui lòng kiểm tra lại thông tin đăng nhập.');
    }
  });
}

const registerForm = document.getElementById('registerForm');
if (registerForm) {
  registerForm.addEventListener('submit', (event) => {
    const form = event.currentTarget;
    clearErrors(form);

    const fullname = form.fullname;
    const phone = form.phone;
    const email = form.email;
    const password = form.password;
    const confirm = form.confirm;
    const terms = form.terms;
    let isValid = true;

    isValid = validateRequired(fullname, 'họ tên') && isValid;
    isValid = validateRequired(phone, 'số điện thoại') && isValid;
    isValid = validateRequired(email, 'email') && isValid;
    isValid = validateRequired(password, 'mật khẩu') && isValid;
    isValid = validateRequired(confirm, 'nhập lại mật khẩu') && isValid;

    if (phone.value && !/^(0|\+84)[0-9]{9,10}$/.test(phone.value.trim())) {
      setError(phone, 'Số điện thoại chưa đúng định dạng.');
      isValid = false;
    }

    if (email.value && !email.checkValidity()) {
      setError(email, 'Email chưa đúng định dạng.');
      isValid = false;
    }

    if (password.value && password.value.length < 6) {
      setError(password, 'Mật khẩu cần tối thiểu 6 ký tự.');
      isValid = false;
    }

    if (password.value && confirm.value && password.value !== confirm.value) {
      setError(confirm, 'Mật khẩu nhập lại chưa khớp.');
      isValid = false;
    }

    if (!terms.checked) {
      const termsError = document.querySelector('.terms-error');
      if (termsError) termsError.textContent = 'Bạn cần đồng ý điều khoản sử dụng.';
      isValid = false;
    }

    if (!isValid) {
      event.preventDefault();
      showToast('Vui lòng kiểm tra lại thông tin đăng ký.');
    }
  });
}
