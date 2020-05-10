package edu.cornell.gdiac.nightbite.entity;

import com.badlogic.gdx.math.Vector2;
import edu.cornell.gdiac.nightbite.AILattice;
import edu.cornell.gdiac.nightbite.Assets;
import edu.cornell.gdiac.nightbite.WorldModel;
import edu.cornell.gdiac.util.SoundController;


public class OilEnemyModel extends EnemyModel {
    private static final int DROP_COOLDOWN = 150;
    private static final float DROP_DIST = 3f;
    private int dropCooldown = 0;

    public OilEnemyModel(float x, float y, WorldModel world) {
        super(
                x, y,
                Assets.getFilmStrip("character/Enemies/E2_64_Walk_FS_8.png"),
                Assets.getFilmStrip("character/Enemies/E2_64_Falling_FS_5.png"),
                world
        );
    }

    public void attack(PlayerModel p, AILattice aiLattice) {
        // Cool down before dropping another oil
        if (dropCooldown > 0) {
            dropCooldown--;
            return;
        }

        // Drop oil if close to player
        Vector2 targetPosition = p.getPosition();
        Vector2 enemyPosition = getPosition();
        float distance = Vector2.dst(targetPosition.x, targetPosition.y, enemyPosition.x, enemyPosition.y);
        if (distance <= DROP_DIST && OilModel.canAdd()) {
            worldModel.addOil(enemyPosition.x, enemyPosition.y);
            dropCooldown = DROP_COOLDOWN;
            SoundController.getInstance().play("audio/oildrip.wav", "audio/oildrip.wav", false, Assets.VOLUME + 0.3f);
        }
    }
}