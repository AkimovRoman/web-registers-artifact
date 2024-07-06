// Глобальные переменные для хранения выбранных пользователя и роли
let selectedUserId = null;
let selectedRoleId = null;

document.addEventListener('DOMContentLoaded', (event) => {
    const usersTable = document.getElementById('usersTable');
    const rolesTable = document.getElementById('rolesTable');

    // Сортировка таблиц по ID
    sortTable(usersTable, 0);
    sortTable(rolesTable, 0);

    // Обработка кликов на строки таблицы пользователей
    usersTable.querySelectorAll('tbody tr').forEach(row => {
        row.addEventListener('click', () => {
            // Удаление выделения со всех строк и добавление к выбранной
            usersTable.querySelectorAll('tbody tr').forEach(r => r.classList.remove('selected'));
            row.classList.add('selected');
            selectedUserId = row.getAttribute('data-user-id');
            // Активируем кнопки редактирования и удаления
            document.getElementById('editBtn').disabled = false;
            document.getElementById('deleteBtn').disabled = false;
        });
    });

    // Обработка кликов на строки таблицы ролей
    rolesTable.querySelectorAll('tbody tr').forEach(row => {
        row.addEventListener('click', () => {
            // Удаление выделения со всех строк и добавление к выбранной
            rolesTable.querySelectorAll('tbody tr').forEach(r => r.classList.remove('selected'));
            row.classList.add('selected');
            selectedRoleId = row.getAttribute('data-role-id');
            // Активируем кнопки редактирования и удаления ролей
            document.getElementById('editRoleBtn').disabled = false;
            document.getElementById('deleteRoleBtn').disabled = false;
        });
    });
});

// Открытие модального окна для добавления пользователя
function addUser() {
    openModal('addUserModal');
}

// Открытие модального окна для редактирования пользователя
function editUser() {
    if (selectedUserId) {
        const userRow = document.querySelector(`#usersTable tr[data-user-id="${selectedUserId}"]`);
        document.getElementById('editUserId').value = selectedUserId;
        document.getElementById('editUserName').value = userRow.cells[1].innerText;
        document.getElementById('editUserPassword').value = userRow.cells[2].innerText;

        const userRole = userRow.cells[3].innerText;
        const roleOptions = document.getElementById('editUserRole').options;
        for (let option of roleOptions) {
            if (option.text === userRole) {
                option.selected = true;
                break;
            }
        }

        openModal('editUserModal');
    }
}

// Открытие модального окна для удаления пользователя
function deleteUser() {
    if (selectedUserId) {
        document.getElementById('deleteUserId').value = selectedUserId;
        openModal('deleteUserModal');
    }
}

// Открытие модального окна для добавления роли
function addRole() {
    openModal('addRoleModal');
}

// Открытие модального окна для редактирования роли
function editRole() {
    if (selectedRoleId) {
        const roleRow = document.querySelector(`#rolesTable tr[data-role-id="${selectedRoleId}"]`);
        document.getElementById('editRoleId').value = selectedRoleId;
        document.getElementById('editRoleName').value = roleRow.cells[1].innerText;
        document.getElementById('editRoleDescription').value = roleRow.cells[2].innerText;
        openModal('editRoleModal');
    }
}

// Открытие модального окна для удаления роли
function deleteRole() {
    if (selectedRoleId) {
        document.getElementById('deleteRoleId').value = selectedRoleId;
        openModal('deleteRoleModal');
    }
}

// Открытие модального окна
function openModal(modalId) {
    document.getElementById(modalId).style.display = 'flex';
}

// Закрытие модального окна
function closeModal(modalId) {
    document.getElementById(modalId).style.display = 'none';
}

// Закрытие модального окна при клике вне его
window.onclick = function(event) {
const modals = document.querySelectorAll('.modal');
modals.forEach(modal => {
        if (event.target == modal) {
            modal.style.display = 'none';
        }
    });
}

// Функция для сортировки таблицы по столбцу
function sortTable(table, column) {
const rows = Array.from(table.querySelector('tbody').rows);
rows.sort((rowA, rowB) => {
        const cellA = rowA.cells[column].innerText;
        const cellB = rowB.cells[column].innerText;
        return parseInt(cellA) - parseInt(cellB);
    });
    rows.forEach(row => table.querySelector('tbody').appendChild(row));
}