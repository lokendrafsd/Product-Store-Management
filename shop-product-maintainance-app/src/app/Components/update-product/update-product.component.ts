import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from 'src/app/Model/Product';
import { ProductService } from 'src/app/Services/product.service';

@Component({
  selector: 'app-update-product',
  templateUrl: './update-product.component.html',
  styleUrls: ['./update-product.component.css']
})
export class UpdateProductComponent implements OnInit {
  id!: number;
  product!: Product;

  constructor(private route: ActivatedRoute,private router: Router,
    private productService: ProductService) { }

  ngOnInit() {
    this.product = new Product();

    this.id = this.route.snapshot.params['id'];
    
    this.productService.getProduct(this.id)
      .subscribe((data: Product) => {
        console.log(data)
        this.product = data;
      }, (error: any) => console.log(error));
  }

  updateProduct() {
    this.productService.updateProduct(this.id, this.product)
      .subscribe((data: any) => {
        console.log(data);
        this.product = new Product();
        this.gotoList();
      }, (error: any) => console.log(error));
  }

  onSubmit() {
    this.updateProduct();    
  }

  gotoList() {
    this.router.navigate(['/products']);
  }
}