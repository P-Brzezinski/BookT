package pl.brzezinski.bookt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.brzezinski.bookt.model.restaurantMenu.Meal;
import pl.brzezinski.bookt.repository.MealRepository;

import java.util.List;

@Service
public class MealService implements GenericService<Long, Meal> {

    private MealRepository mealRepository;

    @Autowired
    public MealService(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    @Override
    public Meal get(Long id) {
        return mealRepository.getOne(id);
    }

    @Override
    public void add(Meal meal) {
        mealRepository.save(meal);
    }

    @Override
    public void remove(Meal meal) {
        mealRepository.delete(meal);
    }

    @Override
    public void deleteById(Long id) {
        mealRepository.deleteById(id);
    }

    @Override
    public List<Meal> getAll() {
        return mealRepository.findAll();
    }
}
