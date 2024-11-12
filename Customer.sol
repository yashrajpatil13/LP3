// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

contract CustomerData {
    
    struct Customer {
        uint id;
        string name;
        uint age;
        string phoneNo;
    }

    Customer[] private customers;

    mapping(uint => bool) public customerExists;
    mapping(uint => Customer) private customerData;

    event CustomerAdded(uint id, string name, uint age, string phoneNo);

    // Receive and fallback functions to accept Ether
    receive() external payable {}
    fallback() external payable {}

    // Add a new customer
    function addCustomer(uint _id, string memory _name, uint _age, string memory _phoneNo) public {
        require(!customerExists[_id], "Customer ID already exists.");
        Customer memory newCustomer = Customer(_id, _name, _age, _phoneNo);
        customers.push(newCustomer);
        customerExists[_id] = true;
        customerData[_id] = newCustomer;
        emit CustomerAdded(_id, _name, _age, _phoneNo);
    }

    // Get the total number of customers
    function getCustomerCount() public view returns (uint) {
        return customers.length;
    }
    
    // Retrieve customer details by ID
    function getCustomerById(uint _id) public view returns (uint, string memory, uint, string memory) {
        require(customerExists[_id], "Customer ID does not exist.");
        Customer memory customer = customerData[_id];
        return (customer.id, customer.name, customer.age, customer.phoneNo);
    }

    // Retrieve all customers
    function getAllCustomers() public view returns (Customer[] memory) {
        return customers;
    }

    // Retrieve all customer names (if you only need names)
    function getAllCustomerNames() public view returns (string[] memory) {
        string[] memory names = new string[](customers.length);
        for (uint i = 0; i < customers.length; i++) {
            names[i] = customers[i].name;
        }
        return names;
    }
}
