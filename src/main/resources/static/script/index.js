const signUpButton = document.getElementById('signUp');
const signInButton = document.getElementById('signIn');
const container = document.getElementById('container');

signUpButton.addEventListener('click', () => {
	container.classList.add("right-panel-active");
});

signInButton.addEventListener('click', () => {
	container.classList.remove("right-panel-active");
});



        const customDropdown = document.querySelector('.custom-dropdown');
        customDropdown.addEventListener('click', function () {
            this.classList.toggle('open');
        });

        const customDropdownOptions = document.querySelectorAll('.custom-dropdown-option');
        customDropdownOptions.forEach(option => {
            option.addEventListener('click', function () {
                const value = this.getAttribute('th:value');
                document.getElementById('theme').value = value;
                customDropdown.querySelector('.custom-dropdown-label').textContent = this.textContent;
                customDropdown.classList.remove('open');
            });
        });