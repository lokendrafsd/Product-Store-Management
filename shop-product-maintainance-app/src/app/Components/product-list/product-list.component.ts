import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Product } from 'src/app/Model/Product';
import { ProductService } from 'src/app/Services/product.service';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {
  product!: Observable<Product[]>;
  public query: any = '';
  public deleteSuccessful: boolean = false;


  constructor(private productService: ProductService,
    private router: Router) { }

  ngOnInit() {
    this.reloadData();
    this.deleteSuccessful = false;
  }

  reloadData() {
    this.product = this.productService.getProductsList();
  }

  deleteProduct(id: number) {

    this.productService.deleteProduct(id)
      .subscribe(
        (data: any) => {
          console.log(data);
          this.reloadData();
          setTimeout(() => {
            this.deleteSuccessful = false;
          }, 3000);
          this.deleteSuccessful = true;
        },
        (error: any) => console.log(error));
  }

  productDetails(id: number) {
    this.router.navigate(['details', id]);
  }

  updateProduct(id: number) {
    this.router.navigate(['update', id]);
  }
}
