import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'productSearch'
})
export class ProductSearchPipe implements PipeTransform {

  transform(value: any, args?: any): any {


    if (!value) return null;
    if (!args) return value;


    return value.filter((item: { productName: string; }) => item.productName.toLowerCase().indexOf(args.toLowerCase()) !== -1);

  }
}
