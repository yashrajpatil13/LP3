// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;
contract ProductInventory {
    struct Product {
        uint256 prodId;
        string name;
        uint256 quantity;
        uint256 price;
    }

    mapping(uint256 => Product) private products;
    uint256 public Count;

    function receiveProduct(string memory _name, uint256 _quantity, uint256 _price) public {
        Count++;
        products[Count] = Product(Count, _name, _quantity, _price);
    }

    function saleProduct(uint256 _Id, uint256 _quantity) public {
        require(_Id > 0 && _Id <= Count, "Invalid product ID");
        Product storage product = products[_Id];
        require(product.quantity >= _quantity, "Insufficient stock");
        product.quantity -= _quantity;
    }
    
    function displayStock(uint256 _Id) public view returns (uint256, string memory, uint256, uint256) {
        require(_Id > 0 && _Id <= Count, "Invalid product ID");
        Product memory product = products[_Id];
        return (product.prodId, product.name, product.quantity, product.price);
    }
}
