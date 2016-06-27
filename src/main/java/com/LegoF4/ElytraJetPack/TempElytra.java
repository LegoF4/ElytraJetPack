package com.LegoF4.ElytraJetPack;

import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class TempElytra {
	if (this.isElytraFlying())
    {
        if (this.motionY > -0.5D)
        {
            this.fallDistance = 1.0F;
        }

        Vec3d vec3d = this.getLookVec();
        float f = this.rotationPitch * 0.017453292F;
        double d6 = Math.sqrt(vec3d.xCoord * vec3d.xCoord + vec3d.zCoord * vec3d.zCoord);
        double d8 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        double d1 = vec3d.lengthVector();
        float f4 = MathHelper.cos(f);
        f4 = (float)((double)f4 * (double)f4 * Math.min(1.0D, d1 / 0.4D));
        this.motionY += -0.08D + (double)f4 * 0.06D;

        if (this.motionY < 0.0D && d6 > 0.0D)
        {
            double d2 = this.motionY * -0.1D * (double)f4;
            this.motionY += d2;
            this.motionX += vec3d.xCoord * d2 / d6;
            this.motionZ += vec3d.zCoord * d2 / d6;
        }

        if (f < 0.0F)
        {
            double d9 = d8 * (double)(-MathHelper.sin(f)) * 0.04D;
            this.motionY += d9 * 3.2D;
            this.motionX -= vec3d.xCoord * d9 / d6;
            this.motionZ -= vec3d.zCoord * d9 / d6;
        }

        if (d6 > 0.0D)
        {
            this.motionX += (vec3d.xCoord / d6 * d8 - this.motionX) * 0.1D;
            this.motionZ += (vec3d.zCoord / d6 * d8 - this.motionZ) * 0.1D;
        }

        this.motionX *= 0.9900000095367432D;
        this.motionY *= 0.9800000190734863D;
        this.motionZ *= 0.9900000095367432D;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);

        if (this.isCollidedHorizontally && !this.worldObj.isRemote)
        {
            double d10 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            double d3 = d8 - d10;
            float f5 = (float)(d3 * 10.0D - 3.0D);

            if (f5 > 0.0F)
            {
                this.playSound(this.getFallSound((int)f5), 1.0F, 1.0F);
                this.attackEntityFrom(DamageSource.flyIntoWall, f5);
            }
        }

        if (this.onGround && !this.worldObj.isRemote)
        {
            this.setFlag(7, false);
        }
    }
}
