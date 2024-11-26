import {Component, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {RegisterService} from '../../services/register.service';
import {DateUtilsService} from '../../utils/date-utils.service';
import {NgIf} from '@angular/common';

@Component({
  selector: 'app-register',
  imports: [
    ReactiveFormsModule,
    NgIf
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent implements OnInit{

  registrationForm: FormGroup;
  errorMessage?: string;
  loading = false;
  maxDate: string = '';
  submitted = false;

  constructor(private fb: FormBuilder,
              private router: Router,
              private registerService: RegisterService,
              private dateUtils: DateUtilsService) {
    this.registrationForm = this.fb.group({
      fullName: ['', [Validators.required, Validators.pattern('^[A-Za-z]+$')]], // Only alphabets, no spaces
      username: ['', [Validators.required, Validators.minLength(5)]], // Only alphabets, no spaces
      password: ['', [Validators.required, Validators.minLength(5)]], // Only alphabets, no spaces
      phone: ['', [Validators.required, Validators.pattern('^[0-9]{10}$')]], // Validates a 10-digit phone number
      gender: ['', Validators.required], // Requires selection of a gender
      email: ['', [Validators.required, Validators.email]],
      dateOfBirth: ['', [Validators.required]]
    });
  }

  ngOnInit(): void {
    this.maxDate = this.dateUtils.getMaxDate(); // Use the utility service
  }

  onSubmit() {
    this.loading = true;
    this.submitted = true;
    if (this.registrationForm.invalid) {
      console.log('Form is invalid:', this.registrationForm.errors);
      return;
    }
    if (this.registrationForm.valid) {
      const formValue = {...this.registrationForm.value};
      // Encode the password using Base64
      formValue.password = btoa(formValue.password);
      console.log('Form Submitted', formValue);
      this.registerService.registerForm(formValue).subscribe((data: any) => {
        if (data.status == 400) {
        } else {
          console.log("Submitted Successfully");
          this.clearForm();
          this.login()
        }
        this.errorMessage = data.status + ' ' + data.message;
        this.loading = false;
      }, (error) => {
        console.error('Error:', error); // Handle error response
      });
    } else {
      console.log('Form is invalid');
      this.loading = false;
      //this.clearForm();
    }
  }

  clearForm() {
    this.registrationForm.reset();
  }

  login() {
    // Navigate to the registration page
    this.router.navigate(['/login']);
  }

  onCheckboxChange(e: any) {
    const rolesArray: FormArray = this.registrationForm.get('roles') as FormArray;

    if (e.target.checked) {
      rolesArray.push(this.fb.control(e.target.value));
    } else {
      const index = rolesArray.controls.findIndex(x => x.value === e.target.value);
      rolesArray.removeAt(index);
    }
  }

}
