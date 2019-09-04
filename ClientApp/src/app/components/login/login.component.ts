import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { UserApi } from 'src/app/services/user.api';
import { ValidationService } from 'src/app/services/validation';
import { Globals } from 'src/app/services/globals';
import { user } from 'src/app/models/user';



@Component({
    selector: 'login-page',
    templateUrl: './login.component.html',
    styleUrls: ['./login.css'],


})
export class LoginPage implements OnInit {
    public loginForm: FormGroup;
    public emailControl: FormControl = new FormControl('', Validators.required);
    public passwordControl: FormControl = new FormControl('', Validators.required);

    public recoveryForm: FormGroup;
    public recoverEmailControl: FormControl = new FormControl('', Validators.required);

    constructor(private loginApi: UserApi,

        private formBuilder: FormBuilder,
        public validator: ValidationService,
        private router: Router,
        private globals: Globals

    ) {

    }

    ngOnInit() {
         
        this.initializeForm();
        this.globals.CreateButtonVisible = false;

    }

	/**
	 * Initializes form group and controls
	 */
    private initializeForm() {
        this.loginForm = this.formBuilder.group(
            {
                'emailControl': this.emailControl,
                'passwordControl': this.passwordControl
            }
        );
    }


   async login(form: any) {
        console.log(form);


        if (!this.loginForm.valid) {

            this.validator.markAsTouched(this.loginForm);
            return;
        }


        this.loginApi
        .login({ username: this.emailControl.value, password: this.passwordControl.value })
        .then(response => {

            console.log(response);
 
              this.globals.currentUser = {
                 Name: this.emailControl.value,
              };


        });
        

        let user:user  = await this.loginApi.getUserInfo(this.emailControl.value).toPromise();
        localStorage.setItem("userId",user.userId);
        this.router.navigate(['/note']);

       
    }

}
