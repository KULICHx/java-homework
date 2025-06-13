package edu.project4.render;

public record RenderParams(int samples, int iterPerSample, long seed, int symmetry, int skipSteps) {
    public RenderParams withSamples(int newSamples) {
        return new RenderParams(newSamples, this.iterPerSample, this.seed, this.symmetry, this.skipSteps);
    }
}

