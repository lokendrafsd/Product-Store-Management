import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Product } from 'src/app/Model/Product';
import { ProductService } from 'src/app/Services/product.service';

@Component({
  selector: 'app-create-product',
  templateUrl: './create-product.component.html',
  styleUrls: ['./create-product.component.css']
})
export class CreateProductComponent implements OnInit {
  product: Product = new Product();
  submitted = false;

  constructor(private productService: ProductService,
    private router: Router) { }

  ngOnInit() {
  }

  newProduct(): void {
    this.submitted = false;
    this.product = new Product();
  }

  save() {
    this.productService
      .createProduct(this.product).subscribe((data: any) => {
        console.log(data)
        this.product = new Product();
        this.gotoList();
      },
        (error: any) => console.log(error));
  }

  onSubmit() {
    this.submitted = true;
    this.save();
  }

  gotoList() {
    setTimeout(() => {
      this.router.navigate(['/products']);
    }, 3000);

  }
}