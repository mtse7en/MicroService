import { Injectable } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';


@Injectable()
export class ValidationService {

    validate(formControl: FormControl) {
        return !formControl.valid && formControl.touched;
    }
    markAsTouched(form: FormGroup) {
        Object.keys(form.controls).forEach(controlName =>  form.controls[controlName].markAsTouched());
    }
}