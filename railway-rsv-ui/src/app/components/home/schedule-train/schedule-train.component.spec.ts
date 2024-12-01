import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ScheduleTrainComponent } from './schedule-train.component';

describe('ScheduleTrainComponent', () => {
  let component: ScheduleTrainComponent;
  let fixture: ComponentFixture<ScheduleTrainComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ScheduleTrainComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ScheduleTrainComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
