// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

contract StudentData {
    struct Student {
        uint id;
        string name;
        uint age;
    }

    Student[] private students;
    mapping(uint => bool) public studentExists;
    mapping(uint => Student) private studentData;

    event StudentAdded(uint id, string name, uint age);

    // Receive and fallback functions to accept Ether
    receive() external payable {}
    fallback() external payable {}

    // Add a new student
    function addStudent(uint _id, string memory _name, uint _age) public {
        require(!studentExists[_id], "ID already exists.");
        Student memory newStudent = Student(_id, _name, _age);
        students.push(newStudent);
        studentExists[_id] = true;
        studentData[_id] = newStudent;
        emit StudentAdded(_id, _name, _age);
    }

    // Get the total number of students
    function getStudentCount() public view returns (uint) {
        return students.length;
    }
    
    // Retrieve student details by ID
    function getStudentById(uint _id) public view returns (uint, string memory, uint) {
        require(studentExists[_id], "Student ID does not exist.");
        Student memory student = studentData[_id];
        return (student.id, student.name, student.age);
    }

    // Retrieve all students
    function getAllStudents() public view returns (Student[] memory) {
        return students;
    }
}
