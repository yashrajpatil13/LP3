// SPDX-License-Identifier: MIT
pragma solidity ^0.8.26;
contract EmployeeData {
    struct Employee {
        uint id;
        string name;
        uint age;
        string department;
    }

    Employee[] private employees;

    mapping(uint => bool) public employeeExist;
    mapping(uint => Employee) private employeeData;

    event employeeAdded(uint id, string name, uint age, string department);

    receive() external payable {}
    fallback() external payable {}

    function addEmployee(uint _id, string memory _name, uint _age, string memory _department) public {
        require(!employeeExist[_id], "Id already exists");
        Employee memory newEmployee = Employee(_id, _name, _age, _department);
        employees.push(newEmployee);
        employeeExist[_id] = true;
        employeeData[_id] = newEmployee;   
        emit employeeAdded(_id, _name, _age, _department);
    }

    function getEmployeeCount() public view returns (uint) {
        return employees.length;
    }

    function getEmployee(uint _id) public view returns (uint, string memory, uint, string memory) {
        require(employeeExist[_id], "Id does not exist");    
        Employee memory employee = employeeData[_id];
        return (employee.id, employee.name, employee.age, employee.department);
    }

    function getAllEmployee() public view returns (Employee[] memory) {
        return employees;
    }
}
